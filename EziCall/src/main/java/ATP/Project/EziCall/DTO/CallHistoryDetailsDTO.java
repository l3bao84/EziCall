package ATP.Project.EziCall.DTO;

import ATP.Project.EziCall.models.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CallHistoryDetailsDTO {

    private String customerId;
    private String phonenumber;
    private String fullname;
    private Gender gender;
    private String email;
    private String address;
    private List<TicketOverviewDTO> ticketOverviewDTOS;

    public CallHistoryDetailsDTO(String customerId, String phonenumber, String fullname, Gender gender, String email, String address) {
        this.customerId = customerId;
        this.phonenumber = phonenumber;
        this.fullname = fullname;
        this.gender = gender;
        this.email = email;
        this.address = address;
    }
}
