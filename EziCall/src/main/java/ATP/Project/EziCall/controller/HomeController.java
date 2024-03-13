package ATP.Project.EziCall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/hello")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok().body("Hello");
    }
}
