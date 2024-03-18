package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.exception.UnauthorizedNoteCreationException;
import ATP.Project.EziCall.requests.AppendNoteRequest;
import ATP.Project.EziCall.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/{ticketId}")
    public ResponseEntity<?> appendNoteToTicket(@PathVariable Long ticketId,
                                                @Valid @RequestBody AppendNoteRequest request,
                                                BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        return ResponseEntity.ok().body(noteService.appendNoteToTicket(ticketId, request));
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<?> updateNote(@PathVariable Long noteId,
                                                @Valid @RequestBody AppendNoteRequest request,
                                                BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        return ResponseEntity.ok().body(noteService.updateNote(noteId,request));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<?> getNotes(@PathVariable Long ticketId) {
        if(noteService.getNotesByTicketId(ticketId).isEmpty()) {
            return ResponseEntity.ok().body("Ticket này chưa có note nào");
        }
        return ResponseEntity.ok().body(noteService.getNotesByTicketId(ticketId));
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleTicketNotFoundException(ObjectNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedNoteCreationException.class)
    public ResponseEntity<Object> handleUnauthorizedNoteCreationException(UnauthorizedNoteCreationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
