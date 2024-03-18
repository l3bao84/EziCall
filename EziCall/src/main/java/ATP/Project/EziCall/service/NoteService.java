package ATP.Project.EziCall.service;

import ATP.Project.EziCall.DTO.NotesDTO;
import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.exception.UnauthorizedNoteCreationException;
import ATP.Project.EziCall.models.Note;
import ATP.Project.EziCall.models.Ticket;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.repository.NoteRepository;
import ATP.Project.EziCall.repository.TicketRepository;
import ATP.Project.EziCall.requests.AppendNoteRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public Note createNewNote(Ticket ticket, String content) {

        Note note = Note.builder()
                .content(content)
                .notedAt(LocalDateTime.now())
                .ticket(ticket)
                .build();

        return note;
    }

    @Transactional
    public Note appendNoteToTicket(Long ticketId, AppendNoteRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại ticket có id: " + ticketId));

        Note note = createNewNote(ticket, request.getContent());

        ticket.getNotes().add(note);
        ticketRepository.save(ticket);
        return note;
    }

    public Note updateNote(Long noteId, AppendNoteRequest request) {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại note có id: " + noteId));

        User user = userService.getUserByUsername();
        if(!note.getTicket().getAssignedTo().getUserId().equalsIgnoreCase(user.getUserId())) {
            throw new UnauthorizedNoteCreationException("Bạn không được ủy quyền để thực hiện việc này");
        }

        note.setContent(request.getContent());
        noteRepository.save(note);
        return note;
    }

    public List<NotesDTO> getNotesByTicketId(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại ticket có id: " + id));

        return noteRepository.getNotesByTicketId(id);
    }
}
