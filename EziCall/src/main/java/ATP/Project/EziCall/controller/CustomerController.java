package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.exception.FieldAlreadyExistException;
import ATP.Project.EziCall.exception.InvalidFormatException;
import ATP.Project.EziCall.exception.UserNotFoundException;
import ATP.Project.EziCall.models.Customer;
import ATP.Project.EziCall.models.Gender;
import ATP.Project.EziCall.models.Role;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.requests.CustomerRequest;
import ATP.Project.EziCall.service.CustomerService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/search")
    public ResponseEntity<?> findByPhone(@RequestParam("phone") String phone) {
        if(phone.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SĐT không được rỗng");
        }else {
            return ResponseEntity.ok().body(customerService.findByPhone(phone));
        }
    }

    @PostMapping()
    public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerRequest customerRequest,
                                         BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.insertNewCustomer(customerRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id,
                                            @Valid @RequestBody CustomerRequest customerRequest,
                                            BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }
        Customer updatedCustomer = customerService.updateCustomer(id, customerRequest);
        return ResponseEntity.ok().body("Cập nhật thông tin khách hàng thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeCustomer(@PathVariable Long id) {
        customerService.removeCustomer(id);
        return ResponseEntity.ok().body("Xóa thành công khách hàng có id: " + id);
    }


    @GetMapping()
    public ResponseEntity<?> getCutomers() {
        if(customerService.getCustomers().isEmpty()) {
            return ResponseEntity.ok().body("Không có khách hàng nào");
        }
        return ResponseEntity.ok().body(customerService.getCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.getCustomerById(id));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FieldAlreadyExistException.class)
    public ResponseEntity<Object> handleFieldAlreadyExistException(FieldAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
