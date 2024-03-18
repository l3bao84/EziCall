package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.requests.AddTicketRequest;
import ATP.Project.EziCall.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        if(ticketService.getAll().isEmpty()) {
            return ResponseEntity.ok().body("Không có ticket nào trong hệ thống");
        }
        return ResponseEntity.ok().body(ticketService.getAll());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getTicketByStatus(@PathVariable String status) {
        if(ticketService.getTicketByStatus(status).isEmpty()) {
            return ResponseEntity.ok().body("Không có ticket nào trong hệ thống có trạng thái: " + status.toUpperCase());
        }
        return ResponseEntity.ok().body(ticketService.getTicketByStatus(status));
    }

    @PostMapping()
    public ResponseEntity<?> addNewTicket(@Valid @RequestBody AddTicketRequest request,
                                          BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        return ResponseEntity.ok().body(ticketService.addTicket(request));
    }

    @PostMapping("/{ticketId}/close")
    public ResponseEntity<?> closeTicket(@PathVariable Long ticketId) {
        return ResponseEntity.ok().body(ticketService.closeTicket(ticketId));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(ObjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
