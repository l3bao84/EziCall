package ATP.Project.EziCall.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import jakarta.persistence.*;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@Builder
public class Ticket {

    @Id
    @GeneratedValue(generator = "userGenerator")
    @GenericGenerator(name = "userGenerator", strategy = "ATP.Project.EziCall.idgenerator.TicketIdGenerator")
    @Column(name = "ticket_id")
    private String ticketId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = false)
    private User assignedTo;

    @Column(name = "employee_notes", columnDefinition = "TEXT")
    private String employeeNotes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Ticket() {
    }

    public Ticket(String ticketId, Customer customer, User assignedTo, String employeeNotes, TicketStatus status, LocalDateTime createdAt) {
        this.ticketId = ticketId;
        this.customer = customer;
        this.assignedTo = assignedTo;
        this.employeeNotes = employeeNotes;
        this.status = status;
        this.createdAt = createdAt;
    }
}
