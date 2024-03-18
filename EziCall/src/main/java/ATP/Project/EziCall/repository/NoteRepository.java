package ATP.Project.EziCall.repository;

import ATP.Project.EziCall.DTO.NotesDTO;
import ATP.Project.EziCall.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT new ATP.Project.EziCall.DTO.NotesDTO(n.id, n.content, t.customer.fullname, n.notedAt) " +
            "FROM Note n JOIN n.ticket t " +
            "WHERE n.ticket.ticketId = :id")
    List<NotesDTO> getNotesByTicketId(@Param("id") long id);
}
