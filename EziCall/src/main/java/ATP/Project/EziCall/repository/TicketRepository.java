package ATP.Project.EziCall.repository;

import ATP.Project.EziCall.DTO.DetailTicketDTO;
import ATP.Project.EziCall.models.Ticket;
import ATP.Project.EziCall.models.TicketStatus;
import ATP.Project.EziCall.DTO.TicketOverviewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

    @Query("SELECT new ATP.Project.EziCall.DTO.TicketOverviewDTO(t.customer.phoneNumber, t.title, concat(t.createdAt,'') , " +
            "(SELECT n.content FROM Note n WHERE n.ticket.ticketId = t.ticketId ORDER BY n.notedAt DESC LIMIT 1),t.status) FROM Ticket t")
    List<TicketOverviewDTO> getAll();

    @Query("SELECT new ATP.Project.EziCall.DTO.TicketOverviewDTO(t.customer.phoneNumber, t.title, concat(t.createdAt,'') , " +
            "(SELECT n.content FROM Note n WHERE n.ticket.ticketId = t.ticketId ORDER BY n.notedAt DESC LIMIT 1),t.status) FROM Ticket t WHERE t.customer.customerId = :id")
    List<TicketOverviewDTO> getTicketByCustomerId(@Param("id") String id);

    @Query("SELECT new ATP.Project.EziCall.DTO.TicketOverviewDTO(t.customer.phoneNumber, t.title, concat(t.createdAt,'') , " +
            "(SELECT n.content FROM Note n WHERE n.ticket.ticketId = t.ticketId ORDER BY n.notedAt DESC LIMIT 1),t.status) FROM Ticket t " +
            "WHERE t.status = :status")
    List<TicketOverviewDTO> getTicketsByStatus(@Param("status") TicketStatus status);

    @Query("SELECT new ATP.Project.EziCall.DTO.TicketOverviewDTO(t.customer.phoneNumber, t.title, concat(t.createdAt,'') , " +
            "(SELECT n.content FROM Note n WHERE n.ticket.ticketId = t.ticketId ORDER BY n.notedAt DESC LIMIT 1),t.status) FROM Ticket t WHERE t.ticketId = :id")
    TicketOverviewDTO getTicketByTicketId(@Param("id") String id);

    @Query("SELECT new ATP.Project.EziCall.DTO.DetailTicketDTO(t.ticketId, t.title, t.status) FROM Ticket t WHERE t.ticketId = :id")
    Optional<DetailTicketDTO> getDetailTicket(@Param("id") String id);

    @Query("SELECT t.ticketId FROM Ticket t")
    List<String> getTicketIds();
}
