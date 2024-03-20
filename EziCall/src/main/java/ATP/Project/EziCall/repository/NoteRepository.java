package ATP.Project.EziCall.repository;

import ATP.Project.EziCall.DTO.NotesDTO;
import ATP.Project.EziCall.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {

    @Query("SELECT new ATP.Project.EziCall.DTO.NotesDTO(n.id, n.content, t.customer.fullname, n.notedAt) " +
            "FROM Note n JOIN n.ticket t " +
            "WHERE n.ticket.ticketId = :id")
    List<NotesDTO> getNotesByTicketId(@Param("id") String id);

    @Query("SELECT new ATP.Project.EziCall.DTO.NotesDTO(n.id, n.content, t.customer.fullname, n.notedAt) " +
            "FROM Note n JOIN n.ticket t " +
            "WHERE n.id = :id")
    NotesDTO getNoteById(@Param("id") String id);

    @Query("SELECT n.id FROM Note n WHERE n.ticket.ticketId = :id")
    List<String> getNoteIdsByTicketId(@Param("id") String ticketId);

    @Query("SELECT n FROM Note n WHERE n.ticket.ticketId = :id")
    List<Note> findNotesByTicket(@Param("id") String ticketId);
}
