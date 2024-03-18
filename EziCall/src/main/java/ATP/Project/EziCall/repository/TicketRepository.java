package ATP.Project.EziCall.repository;

import ATP.Project.EziCall.models.Ticket;
import ATP.Project.EziCall.models.TicketStatus;
import ATP.Project.EziCall.response.TicketResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT new ATP.Project.EziCall.response.TicketResponse(t.customer.phoneNumber, t.title, t.createdAt, " +
            "(SELECT n.content FROM Note n WHERE n.ticket.ticketId = t.ticketId ORDER BY n.notedAt DESC LIMIT 1),t.status) FROM Ticket t")
    List<TicketResponse> getAll();

    @Query("SELECT new ATP.Project.EziCall.response.TicketResponse(t.customer.phoneNumber, t.title, t.createdAt, " +
            "(SELECT n.content FROM Note n WHERE n.ticket.ticketId = t.ticketId ORDER BY n.notedAt DESC LIMIT 1),t.status) FROM Ticket t WHERE t.customer.customerId = :id")
    List<TicketResponse> getTicketByCustomerId(@Param("id") String id);

    @Query("SELECT new ATP.Project.EziCall.response.TicketResponse(t.customer.phoneNumber, t.title, t.createdAt, " +
            "(SELECT n.content FROM Note n WHERE n.ticket.ticketId = t.ticketId ORDER BY n.notedAt DESC LIMIT 1),t.status) FROM Ticket t " +
            "WHERE t.status = :status")
    List<TicketResponse> getTicketsByStatus(@Param("status") TicketStatus status);
}