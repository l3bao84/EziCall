package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.exception.TicketModificationNotAllowedException;
import ATP.Project.EziCall.requests.AddTicketRequest;
import ATP.Project.EziCall.requests.UpdateTicketRequest;
import ATP.Project.EziCall.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<?> getTicket(@PathVariable String ticketId) {
        return ResponseEntity.ok().body(ticketService.getDetailTicket(ticketId));
    }

    @PutMapping("/update/{ticketId}")
    public ResponseEntity<?> updateTicketTitle(@PathVariable String ticketId,
                                               @Valid @RequestBody UpdateTicketRequest request,
                                               BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        ticketService.updateTicket(ticketId, request);
        return ResponseEntity.ok().body("Lưu thành công");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> getAll() {
        if(ticketService.getAll().isEmpty()) {
            return ResponseEntity.ok().body("Không thấy danh sách ticket");
        }
        return ResponseEntity.ok().body(ticketService.getAll());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getTicketByStatus(@PathVariable String status) {
        if(ticketService.getTicketByStatus(status).isEmpty()) {
            return ResponseEntity.ok().body("Không thấy danh sách ticket");
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
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        ticketService.addTicket(request);
        return ResponseEntity.ok().body("Lưu thành công");
    }

    @PostMapping("/{ticketId}/close")
    public ResponseEntity<?> closeTicket(@PathVariable String ticketId) {
        return ResponseEntity.ok().body(ticketService.closeTicket(ticketId));
    }

    @PostMapping("/{ticketId}/open")
    public ResponseEntity<?> openTicket(@PathVariable String ticketId) {
        return ResponseEntity.ok().body(ticketService.openTicket(ticketId));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(ObjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TicketModificationNotAllowedException.class)
    public ResponseEntity<Object> handleTicketModificationNotAllowedException(TicketModificationNotAllowedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
