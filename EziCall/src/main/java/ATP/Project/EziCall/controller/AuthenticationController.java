package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.exception.AuthenticationFailedException;
import ATP.Project.EziCall.models.UserActivityLog;
import ATP.Project.EziCall.requests.AuthenticationRequest;
import ATP.Project.EziCall.response.AuthenticationResponse;
import ATP.Project.EziCall.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = new AuthenticationResponse(authenticationService.authenticate(request));
        return ResponseEntity.ok().body(response.getToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        UserActivityLog logResult = authenticationService.logout();
        if (logResult != null) {
            return ResponseEntity.ok().body("Bạn đã đăng xuất.");
        } else {
            return ResponseEntity.badRequest().body("Đăng xuất thất bại.");
        }
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<?> handleAuthenticationFailedException(AuthenticationFailedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
}
