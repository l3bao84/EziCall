package ATP.Project.EziCall.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CallHistoryDTO {

    private String phonenumber;
    private String fullname;
    private LocalDateTime latestCalling;
    private long openTicket;
}
