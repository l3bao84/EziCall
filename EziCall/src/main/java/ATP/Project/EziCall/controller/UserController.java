package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.exception.InvalidFormatException;
import ATP.Project.EziCall.exception.RegistrationFailedException;
import ATP.Project.EziCall.exception.UserNotFoundException;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.requests.UserRequest;
import ATP.Project.EziCall.response.UserResponse;
import ATP.Project.EziCall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
//@PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        UserResponse response = userService.getEmployee(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/employee/search")
    public ResponseEntity<?> searchEmployee(@RequestParam(value = "name", required = false) String name) {
        if(name.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên nhân viên không được rỗng");
        }else {
            if(userService.findByName(name).isEmpty()) {
                return ResponseEntity.ok().body("Không có nhân viên nào trong hệ thống có tên: " + name);
            }
            return ResponseEntity.ok().body(userService.findByName(name));
        }
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
    public ResponseEntity<?> updateEmployee(@PathVariable Long id,
                                            @Valid @RequestBody UserRequest request,
                                            BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }
        User updatedUser = userService.updateEmployee(id, request);
        return ResponseEntity.ok().body("Cập nhật thông tin nhân viên thành công");
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
