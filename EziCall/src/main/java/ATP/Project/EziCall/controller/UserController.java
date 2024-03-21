package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.DTO.EmployeeDetailDTO;
import ATP.Project.EziCall.exception.*;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.requests.UserRequest;
import ATP.Project.EziCall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/employees")
    public ResponseEntity<?> getAll() {
        if(userService.getAll().isEmpty()) {
            return ResponseEntity.ok().body("Không thấy danh sách nhân viên");
        }
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable String id) {
        EmployeeDetailDTO employeeDetailDTO = userService.getEmployee(id);
        return ResponseEntity.ok().body(employeeDetailDTO);
    }

    @GetMapping("/employee/search")
    public ResponseEntity<?> searchEmployee(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "id", required = false) String id,
                                            @RequestParam(value = "username", required = false) String username,
                                            @RequestParam(value = "role", required = false) String role) {
        if(userService.findEmployee(name,username,role, id).isEmpty()) {
            return ResponseEntity.ok().body("Không tìm thấy nhân viên phù hợp");
        }
        return ResponseEntity.ok().body(userService.findEmployee(name,username,role, id));
    }

    @GetMapping("/employee/filterByRole")
    public ResponseEntity<?> filterEmployeeByRole(@RequestParam(value = "role", required = false) String role) {
        if(userService.filterByRole(role).isEmpty()) {
            return ResponseEntity.ok().body("Không có nhân viên nào phù hợp");
        }
        return ResponseEntity.ok().body(userService.filterByRole(role));
    }

    @GetMapping("/employee/activities")
    public ResponseEntity<?> getEmployeeActivities(@RequestParam(value = "status", required = false) String status) {
        if((!status.equals("")) && (userService.getEmployeesActivities(status).isEmpty())) {
            return ResponseEntity.ok().body("Không có nhân viên " + status);
        }
        return ResponseEntity.ok().body(userService.getEmployeesActivities(status));
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id,
                                            @Valid @RequestBody UserRequest request,
                                            BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        User updatedUser = userService.updateEmployee(id, request);
        return ResponseEntity.ok().body("Lưu thành công");
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        userService.deleteEmployee(id);
        return ResponseEntity.ok().body("Xóa thành công nhân viên");
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

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<Object> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<Object> handleEmptyListException(EmptyListException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
