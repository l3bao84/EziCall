package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.exception.InvalidFormatException;
import ATP.Project.EziCall.exception.RegistrationFailedException;
import ATP.Project.EziCall.models.Role;
import ATP.Project.EziCall.requests.UserRequest;
import ATP.Project.EziCall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/admin")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody UserRequest request,
                                      BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request, Role.ADMIN));
        }
    }

    @PostMapping("/employee")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> registerEmployee(@Valid @RequestBody UserRequest request,
                                      BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }else {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request, Role.SUPPORTER));
        }
    }


    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<?> handleAuthenticationFailedException(RegistrationFailedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
