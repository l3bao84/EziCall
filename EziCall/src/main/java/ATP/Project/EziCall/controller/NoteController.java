package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.exception.TicketModificationNotAllowedException;
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

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/{ticketId}")
    public ResponseEntity<?> appendNoteToTicket(@PathVariable String ticketId,
                                                @Valid @RequestBody AppendNoteRequest request,
                                                BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        noteService.appendNoteToTicket(ticketId, request);
        return ResponseEntity.ok().body("Lưu thành công");
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<?> updateNote(@PathVariable String noteId,
                                                @Valid @RequestBody AppendNoteRequest request,
                                                BindingResult result) {
        if(result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }
        noteService.updateNote(noteId,request);
        return ResponseEntity.ok().body("Lưu thành công");
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<?> getNotes(@PathVariable String ticketId) {
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

    @ExceptionHandler(TicketModificationNotAllowedException.class)
    public ResponseEntity<Object> handleTicketModificationNotAllowedException(TicketModificationNotAllowedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
