package ATP.Project.EziCall.service;

import ATP.Project.EziCall.DTO.NotesDTO;
import ATP.Project.EziCall.DTO.TicketOverviewDTO;
import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.exception.TicketModificationNotAllowedException;
import ATP.Project.EziCall.exception.UnauthorizedNoteCreationException;
import ATP.Project.EziCall.models.Note;
import ATP.Project.EziCall.models.Ticket;
import ATP.Project.EziCall.models.TicketStatus;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.repository.NoteRepository;
import ATP.Project.EziCall.repository.TicketRepository;
import ATP.Project.EziCall.requests.AppendNoteRequest;
import ATP.Project.EziCall.util.AppConstants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteService {

    private final TicketRepository ticketRepository;
    private final NoteRepository noteRepository;
    private final UserService userService;

    @Autowired
    public NoteService(TicketRepository ticketRepository, NoteRepository noteRepository, UserService userService) {
        this.ticketRepository = ticketRepository;
        this.noteRepository = noteRepository;
        this.userService = userService;
    }

    public String generateNewId(String ticketId) {
        if(noteRepository.findNotesByTicket(ticketId).isEmpty()) {
            return ticketId + (String.format("%03d", 1));
        }
        List<String> noteIds = noteRepository.getNoteIdsByTicketId(ticketId);
        long max = noteIds.stream()
                .map(note -> note.substring(note.length() - 3))
                .mapToLong(Long::parseLong)
                .max()
                .orElse(0L);
        return ticketId + (String.format("%03d", max + 1));
    }

    @Transactional
    public Note createNewNote(Ticket ticket, String content) {

        return Note.builder()
                .id(generateNewId(ticket.getTicketId()))
                .content(content)
                .notedAt(LocalDateTime.now())
                .ticket(ticket)
                .build();
    }

    @Transactional
    public TicketOverviewDTO appendNoteToTicket(String ticketId, AppendNoteRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.TICKET_IS_NOT_EXIST));

        if(ticket.getStatus().equals(TicketStatus.CLOSED)) {
            throw new TicketModificationNotAllowedException(AppConstants.TICKET_IS_CLOSED);
        }

        Note note = createNewNote(ticket, request.getContent());

        ticket.getNotes().add(note);
        ticketRepository.save(ticket);
        return ticketRepository.getTicketByTicketId(ticketId);
    }

    public NotesDTO updateNote(String noteId, AppendNoteRequest request) {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.NOTE_IS_NOT_EXIST));

        if(note.getTicket().getStatus().equals(TicketStatus.CLOSED)) {
            throw new TicketModificationNotAllowedException(AppConstants.TICKET_IS_CLOSED);
        }

        User user = userService.getUserByUsername();
        if(!note.getTicket().getAssignedTo().getUserId().equalsIgnoreCase(user.getUserId())) {
            throw new UnauthorizedNoteCreationException("Bạn không được ủy quyền để thực hiện việc này");
        }

        note.setContent(request.getContent());
        note.setNotedAt(LocalDateTime.now());
        noteRepository.save(note);
        return noteRepository.getNoteById(note.getId());
    }

    public List<NotesDTO> getNotesByTicketId(String id) {
        ticketRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.TICKET_IS_NOT_EXIST + id));

        return noteRepository.getNotesByTicketId(id);
    }
}
