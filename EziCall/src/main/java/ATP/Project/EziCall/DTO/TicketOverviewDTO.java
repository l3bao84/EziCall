package ATP.Project.EziCall.DTO;

import ATP.Project.EziCall.models.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TicketOverviewDTO {

    private String phonenumber;
    private String title;
    private String createdAt;
    private String latestNote;
    private TicketStatus status;
}
