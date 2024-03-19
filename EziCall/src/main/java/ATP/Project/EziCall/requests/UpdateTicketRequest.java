package ATP.Project.EziCall.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTicketRequest {

    @NotBlank(message = "Không được để trống tiêu đề")
    private String title;
}
