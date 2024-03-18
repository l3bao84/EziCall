package ATP.Project.EziCall.service;

import ATP.Project.EziCall.DTO.CallHistoryDTO;
import ATP.Project.EziCall.DTO.CallHistoryDetailsDTO;
import ATP.Project.EziCall.exception.FieldAlreadyExistException;
import ATP.Project.EziCall.exception.InvalidFormatException;
import ATP.Project.EziCall.exception.RegistrationFailedException;
import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.models.*;
import ATP.Project.EziCall.repository.CustomerRepository;
import ATP.Project.EziCall.requests.CustomerRequest;
import ATP.Project.EziCall.response.CustomerResponse;
import ATP.Project.EziCall.util.DataValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

    public CustomerResponse findByPhone(String phonenumber) {
        CustomerResponse customer = customerRepository.findByPhone(phonenumber)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại khách hàng có sđt: " + phonenumber));

        return customer;
    }

    public List<CustomerResponse> getCustomers() {
        return customerRepository.getAll();
    }

    public CustomerResponse getCustomerById(String id) {
        CustomerResponse customer = customerRepository.getCustomerById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại khách hàng có id: " + id));

        return customer;
    }

    private void validateCustomerData(CustomerRequest customerRequest) throws InvalidFormatException, RegistrationFailedException {
        if (!dataValidation.isValidDataCustomer(customerRequest.getEmail(), customerRequest.getPhonenumber())) {
            throw new InvalidFormatException("Vui lòng nhập đúng định dạng");
        }

        customerRepository.findByEmail(customerRequest.getEmail())
                .ifPresent(s -> {
                    throw new FieldAlreadyExistException("Email này đã được sử dụng");
                });

        customerRepository.findByPhone(customerRequest.getPhonenumber())
                .ifPresent(s -> {
                    throw new FieldAlreadyExistException("Số điện thoại này đã được sử dụng");
                });
    }

    @Transactional
    public CustomerResponse insertNewCustomerAndTicket(CustomerRequest customerRequest) {
        validateCustomerData(customerRequest);

        Customer customer = Customer.builder()
                .fullname(customerRequest.getFullname())
                .email(customerRequest.getEmail())
                .phoneNumber(customerRequest.getPhonenumber())
                .address(customerRequest.getAddress())
                .tickets(new HashSet<>())
                .gender(Gender.valueOf(customerRequest.getGender())).build();

        customerRepository.save(customer);

        User user = userService.getUserByUsername();

        Ticket ticket = ticketService.createNewTicket(customer, customerRequest.getTitle(), customerRequest.getNote(), user);

        customer.getTickets().add(ticket);
        return customerRepository.getCustomerById(customer.getCustomerId()).get();
    }

    public Customer updateCustomer(String id, CustomerRequest customerRequest) {
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

        callHistoryDetailsDTO.setTicketResponse(ticketService.getTicketsByCustomerId(id));

        return callHistoryDetailsDTO;
    }
}
