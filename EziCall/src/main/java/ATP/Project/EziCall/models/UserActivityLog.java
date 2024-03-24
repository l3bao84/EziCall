package ATP.Project.EziCall.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "employee_activity_log")
@Getter
@Setter
public class UserActivityLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "activity_type", nullable = false)
    private String status;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public UserActivityLog() {
    }

    public UserActivityLog(User user, String status, LocalDateTime timestamp) {
        this.user = user;
        this.status = status;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserActivityLog log = (UserActivityLog) o;
        return Objects.equals(id, log.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
