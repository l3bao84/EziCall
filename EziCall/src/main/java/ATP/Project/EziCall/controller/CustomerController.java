package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.exception.FieldAlreadyExistException;
import ATP.Project.EziCall.exception.InvalidFormatException;
import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.models.Customer;
import ATP.Project.EziCall.requests.CustomerRequest;
import ATP.Project.EziCall.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/call-details")
    public ResponseEntity<?> getCallHistory() {
        if(customerService.getCallHistory().isEmpty()) {
            return ResponseEntity.ok().body("Không có danh sách cuộc gọi nào");
        }
        return ResponseEntity.ok().body(customerService.getCallHistory());
    }

    @GetMapping("/call-details/{id}")
    public ResponseEntity<?> getCallHistoryDetails(@PathVariable String id) {
        return ResponseEntity.ok().body(customerService.getCallHistoryDetails(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> findByPhone(@RequestParam("phone") String phone,
                                         @RequestParam("name") String name) {
        return ResponseEntity.ok().body(customerService.findByPhone(phone));
    }

    @PostMapping("")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerRequest customerRequest,
                                         BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.insertNewCustomerAndTicket(customerRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id,
                                            @Valid @RequestBody CustomerRequest customerRequest,
                                            BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        Customer updatedCustomer = customerService.updateCustomer(id, customerRequest);
        return ResponseEntity.ok().body("Cập nhật thông tin khách hàng thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeCustomer(@PathVariable String id) {
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
    public ResponseEntity<?> getCustomer(@PathVariable String id) {
        return ResponseEntity.ok().body(customerService.getCustomerById(id));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(ObjectNotFoundException ex) {
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
