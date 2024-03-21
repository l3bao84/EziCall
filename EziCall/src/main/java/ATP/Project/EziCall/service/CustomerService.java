package ATP.Project.EziCall.service;

import ATP.Project.EziCall.DTO.CallHistoryDTO;
import ATP.Project.EziCall.DTO.CallHistoryDetailsDTO;
import ATP.Project.EziCall.DTO.CustomerDTO;
import ATP.Project.EziCall.DTO.CustomerDetailDTO;
import ATP.Project.EziCall.exception.*;
import ATP.Project.EziCall.models.*;
import ATP.Project.EziCall.repository.CustomerRepository;
import ATP.Project.EziCall.requests.CustomerRequest;
import ATP.Project.EziCall.requests.UpdateCustomerRequest;
import ATP.Project.EziCall.util.DataValidation;
import ATP.Project.EziCall.util.Validatable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DataValidation dataValidation;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    public List<CustomerDTO> findCustomer(String phonenumber, String name) {

        return customerRepository.findCustomer(phonenumber,name);
    }

    public List<CustomerDTO> findCustomer(String phonenumber, String name, String id, String gender) {
        Gender gendr = null;
        if (gender != null && !gender.isEmpty()) {
            try {
                gendr = Gender.valueOf(gender);
            } catch (IllegalArgumentException e) {

            }
        }
        return customerRepository.findCustomer(phonenumber,name, id, gendr);
    }

    public List<CustomerDTO> getCustomers() {
        if(customerRepository.findAll().isEmpty()) {
            throw new EmptyListException("Không thấy danh sách khách hàng");
        }
        List<CustomerDTO> customerDTOS = customerRepository.getAll();
        for (CustomerDTO customerDTO:customerDTOS) {
            if(customerDTO.getNumberCall() == null) {
                customerDTO.setNumberCall(0L);
            }
        }

        return customerRepository.getAll();
    }

    public CustomerDetailDTO getCustomerById(String id) {
        CustomerDetailDTO customer = customerRepository.getCustomerById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại khách hàng có id: " + id));

        return customer;
    }

    private void validateCustomerData(Validatable data) throws InvalidFormatException, RegistrationFailedException {
        if (!dataValidation.isValidDataCustomer(data.getEmail(), data.getPhonenumber())) {
            throw new InvalidFormatException("Email không hợp lệ");
        }

        customerRepository.findCustomerByPhoneNumber(data.getPhonenumber())
                .ifPresent(s -> {
                    throw new FieldAlreadyExistException("SĐT đã có trong hệ thống");
                });
    }

    @Transactional
    public CustomerDetailDTO insertNewCustomerAndTicket(CustomerRequest customerRequest, String value) {
        validateCustomerData(customerRequest);

        Customer customer = Customer.builder()
                .fullname(customerRequest.getFullname())
                .email(customerRequest.getEmail())
                .phoneNumber(customerRequest.getPhonenumber())
                .address(customerRequest.getAddress())
                .tickets(new HashSet<>())
                .gender(Gender.valueOf(customerRequest.getGender())).build();

        customerRepository.save(customer);

        if(value.equalsIgnoreCase("true")) {
            if(customerRequest.getTitle().equals("")) {
                throw new InvalidFormatException("Tiêu đề không được để trống");
            }else {
                User user = userService.getUserByUsername();

                Ticket ticket = ticketService.createNewTicket(customer, customerRequest.getTitle(), user);
                Note note = noteService.createNewNote(ticket, customerRequest.getNote());
                ticket.getNotes().add(note);

                customer.getTickets().add(ticket);
            }
        }
        return customerRepository.getCustomerById(customer.getCustomerId()).get();
    }

    public Customer updateCustomer(String id, UpdateCustomerRequest customerRequest) {
        validateCustomerData(customerRequest);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại khách hàng có id: " + id));

        customer.setFullname(customerRequest.getFullname());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhonenumber());
        customer.setAddress(customerRequest.getAddress());
        customer.setGender(Gender.valueOf(customerRequest.getGender()));

        return customerRepository.save(customer);
    }

    public void removeCustomer(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại khách hàng có id: " + id));

        customerRepository.delete(customer);
    }

    public List<CallHistoryDTO> getCallHistory() {
        return customerRepository.getCallHistory();
    }

    public CallHistoryDetailsDTO getCallHistoryDetails(String id) {
        CallHistoryDetailsDTO callHistoryDetailsDTO = customerRepository.getCallHistoryDetails(id)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại khách hàng có id: " + id));

        if(ticketService.getTicketsByCustomerId(id).isEmpty()) {
            throw new EmptyListException("Không có danh sách ticket");
        }

        callHistoryDetailsDTO.setTicketOverviewDTOS(ticketService.getTicketsByCustomerId(id));

        return callHistoryDetailsDTO;
    }
}
