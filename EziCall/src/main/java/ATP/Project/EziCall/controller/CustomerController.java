package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.exception.EmptyListException;
import ATP.Project.EziCall.exception.FieldAlreadyExistException;
import ATP.Project.EziCall.exception.InvalidFormatException;
import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.models.Customer;
import ATP.Project.EziCall.requests.CustomerRequest;
import ATP.Project.EziCall.requests.UpdateCustomerRequest;
import ATP.Project.EziCall.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/call-details")
    public ResponseEntity<?> getCallHistory() {
        if(customerService.getCallHistory().isEmpty()) {
            return ResponseEntity.ok().body("Không thấy danh sách lịch sử cuộc gọi");
        }
        return ResponseEntity.ok().body(customerService.getCallHistory());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/call-details/{id}")
    public ResponseEntity<?> getCallHistoryDetails(@PathVariable String id) {
        return ResponseEntity.ok().body(customerService.getCallHistoryDetails(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> findCustomer(@RequestParam("phone") String phone,
                                         @RequestParam("name") String name) {
        if(customerService.findCustomer(phone,name).isEmpty()) {
            System.out.println(customerService.findCustomer(phone,name).size());
            return ResponseEntity.ok().body("Không tìm thấy khách hàng phù hợp");
        }
        return ResponseEntity.ok().body(customerService.findCustomer(phone, name));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/search")
    public ResponseEntity<?> findCustomer(@RequestParam("phone") String phone,
                                          @RequestParam("name") String name,
                                          @RequestParam("id") String id,
                                          @RequestParam("gender") String gender) {
        if(customerService.findCustomer(phone,name,id,gender).isEmpty()) {
            return ResponseEntity.ok().body("Không tìm thấy khách hàng phù hợp");
        }
        return ResponseEntity.ok().body(customerService.findCustomer(phone,name,id,gender));
    }

    @PostMapping("")
    public ResponseEntity<?> addCustomer(@RequestParam("addTicket") String value,
                                         @Valid @RequestBody CustomerRequest customerRequest,
                                         BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        customerService.insertNewCustomerAndTicket(customerRequest, value);
        return ResponseEntity.status(HttpStatus.CREATED).body("Thêm khách hàng thành công");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id,
                                            @Valid @RequestBody UpdateCustomerRequest request,
                                            BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        Customer updatedCustomer = customerService.updateCustomer(id, request);
        return ResponseEntity.ok().body("Cập nhật thông tin khách hàng thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeCustomer(@PathVariable String id) {
        customerService.removeCustomer(id);
        return ResponseEntity.ok().body("Xóa khách hàng thành công");
    }


    @PreAuthorize("hasAuthority('ADMIN')")
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

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<Object> handleEmptyListException(EmptyListException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
