package ATP.Project.EziCall.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTicketRequest {

    @NotBlank(message = "Không được để trống số điện thoại")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại chỉ bao gồm 10 chữ số tự nhiên")
    private String phonenumber;

    private String status;

    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    private String notes;

}
