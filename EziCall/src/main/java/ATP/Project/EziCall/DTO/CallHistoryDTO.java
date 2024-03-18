package ATP.Project.EziCall.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallHistoryDTO {

    private String customerId;
    private String phonenumber;
    private String fullname;
    private LocalDateTime latestCalling;
    private long openTicket;
}
