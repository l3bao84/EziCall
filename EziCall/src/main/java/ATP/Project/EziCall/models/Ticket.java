package ATP.Project.EziCall.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

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

    public Ticket(Long ticketId, Customer customer, User assignedTo, String employeeNotes, TicketStatus status, LocalDateTime createdAt) {
        this.ticketId = ticketId;
        this.customer = customer;
        this.assignedTo = assignedTo;
        this.employeeNotes = employeeNotes;
        this.status = status;
        this.createdAt = createdAt;
    }
}
