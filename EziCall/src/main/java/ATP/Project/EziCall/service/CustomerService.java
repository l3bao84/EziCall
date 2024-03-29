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
import ATP.Project.EziCall.util.AppConstants;
import ATP.Project.EziCall.util.DataValidation;
import ATP.Project.EziCall.util.Validatable;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final DataValidation dataValidation;

    private final TicketService ticketService;

    private final UserService userService;

    private final NoteService noteService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, DataValidation dataValidation, TicketService ticketService, UserService userService, NoteService noteService) {
        this.customerRepository = customerRepository;
        this.dataValidation = dataValidation;
        this.ticketService = ticketService;
        this.userService = userService;
        this.noteService = noteService;
    }

    public List<CustomerDTO> findCustomer(String phonenumber, String name) {

        return customerRepository.findCustomer(phonenumber,name);
    }

    public List<CustomerDTO> findCustomer(String phonenumber, String name, String id, String gender) {
        Gender gendr = null;
        if (gender != null && !gender.isEmpty()) {
            try {
                gendr = Gender.valueOf(gender);
            } catch (IllegalArgumentException e) {
                // Giới tính không hợp lệ sẽ bị bỏ qua và không gán giá trị cho gendr
            }
        }
        return customerRepository.findCustomer(phonenumber,name, id, gendr);
    }

    public List<CustomerDTO> getCustomers() {
        if(customerRepository.findAll().isEmpty()) {
            throw new EmptyListException(AppConstants.NO_DATA_LIST);
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
        return customerRepository.getCustomerById(id)
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.CUS_IS_NOT_EXIST));
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
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.CUS_IS_NOT_EXIST));

        customer.setFullname(customerRequest.getFullname());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhonenumber());
        customer.setAddress(customerRequest.getAddress());
        customer.setGender(Gender.valueOf(customerRequest.getGender()));

        return customerRepository.save(customer);
    }

    public void removeCustomer(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.CUS_IS_NOT_EXIST));

        customerRepository.delete(customer);
    }

    public List<CallHistoryDTO> getCallHistory() {
        return customerRepository.getCallHistory();
    }

    public CallHistoryDetailsDTO getCallHistoryDetails(String id) {
        CallHistoryDetailsDTO callHistoryDetailsDTO = customerRepository.getCallHistoryDetails(id)
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.CUS_IS_NOT_EXIST));

        if(ticketService.getTicketsByCustomerId(id).isEmpty()) {
            throw new EmptyListException(AppConstants.NO_DATA_LIST);
        }

        callHistoryDetailsDTO.setTicketOverviewDTOS(ticketService.getTicketsByCustomerId(id));

        return callHistoryDetailsDTO;
    }
}
