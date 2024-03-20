package ATP.Project.EziCall.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTicketRequest {

    @NotBlank(message = "Không được để trống số điện thoại")
    private String phonenumber;

    private String status;

    @NotBlank(message = "Không được để trống tiêu đề cuộc gọi")
    private String title;

    private String notes;

}
