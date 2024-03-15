package ATP.Project.EziCall.service;

import ATP.Project.EziCall.exception.FieldAlreadyExistException;
import ATP.Project.EziCall.exception.InvalidFormatException;
import ATP.Project.EziCall.exception.RegistrationFailedException;
import ATP.Project.EziCall.exception.UserNotFoundException;
import ATP.Project.EziCall.models.Customer;
import ATP.Project.EziCall.models.Gender;
import ATP.Project.EziCall.repository.CustomerRepository;
import ATP.Project.EziCall.requests.CustomerRequest;
import ATP.Project.EziCall.requests.UserRequest;
import ATP.Project.EziCall.response.CustomerResponse;
import ATP.Project.EziCall.util.DataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DataValidation dataValidation;

    public CustomerResponse findByPhone(String phonenumber) {
        CustomerResponse customer = customerRepository.findByPhone(phonenumber)
                .orElseThrow(() -> new UserNotFoundException("Không tồn tại khách hàng có sđt: " + phonenumber));

        return customer;
    }

    public List<CustomerResponse> getCustomers() {
        return customerRepository.getAll();
    }

    public CustomerResponse getCustomerById(String id) {
        CustomerResponse customer = customerRepository.getCustomerById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tồn tại khách hàng có id: " + id));

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

    public Customer insertNewCustomer(CustomerRequest customerRequest) {
        validateCustomerData(customerRequest);

        Customer customer = Customer.builder()
                .fullname(customerRequest.getFullname())
                .email(customerRequest.getEmail())
                .phoneNumber(customerRequest.getPhonenumber())
                .address(customerRequest.getAddress())
                .gender(Gender.valueOf(customerRequest.getGender())).build();

        return customerRepository.save(customer);
    }

    public Customer updateCustomer(String id, CustomerRequest customerRequest) {
        validateCustomerData(customerRequest);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tồn tại khách hàng có id: " + id));

        customer.setFullname(customerRequest.getFullname());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhonenumber());
        customer.setAddress(customerRequest.getAddress());
        customer.setGender(Gender.valueOf(customerRequest.getGender()));

        return customerRepository.save(customer);
    }

    public void removeCustomer(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Không tồn tại khách hàng có id: " + id));

        customerRepository.delete(customer);
    }
}
