package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.exception.InvalidFormatException;
import ATP.Project.EziCall.exception.RegistrationFailedException;
import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.requests.UserRequest;
import ATP.Project.EziCall.response.UserResponse;
import ATP.Project.EziCall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/employees")
    public ResponseEntity<?> getAll() {
        if(userService.getAll().isEmpty()) {
            return ResponseEntity.ok().body("Không có nhân viên nào trong hệ thống");
        }
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable String id) {
        UserResponse response = userService.getEmployee(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/employee/search")
    public ResponseEntity<?> searchEmployee(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "username", required = false) String username,
                                            @RequestParam(value = "role", required = false) String role) {
        if(userService.findEmployee(name,username,role).isEmpty()) {
            return ResponseEntity.ok().body("Không có kết quả tìm kiếm với từ khóa của bạn");
        }
        return ResponseEntity.ok().body(userService.findEmployee(name,username,role));
    }

    @GetMapping("/employee/filterByRole")
    public ResponseEntity<?> filterEmployeeByRole(@RequestParam(value = "role", required = false) String role) {
        return ResponseEntity.ok().body(userService.filterByRole(role));
    }

    @GetMapping("/employee/online")
    public ResponseEntity<?> getOnlineEmployees() {
        if(userService.findEmpOnline().isEmpty()) {
            return ResponseEntity.ok().body("Không có nhân viên nào trong hệ thống đang online");
        }
        return ResponseEntity.ok().body(userService.findEmpOnline());
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id,
                                            @Valid @RequestBody UserRequest request,
                                            BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }
        User updatedUser = userService.updateEmployee(id, request);
        return ResponseEntity.ok().body("Cập nhật thông tin nhân viên thành công");
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        userService.deleteEmployee(id);
        return ResponseEntity.ok().body("Xóa thành công nhân viên có id: " + id);
    }

    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<?> handleAuthenticationFailedException(RegistrationFailedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(ObjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
