package ATP.Project.EziCall.DTO;

import ATP.Project.EziCall.models.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketOverviewDTO {

    private String phonenumber;
    private String title;
    private LocalDateTime createdAt;
    private String latestNote;
    private TicketStatus status;
}
