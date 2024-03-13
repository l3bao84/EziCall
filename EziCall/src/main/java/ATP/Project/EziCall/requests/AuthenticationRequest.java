package ATP.Project.EziCall.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "Không được để trống trường này")
    private String username;

    @NotBlank(message = "Không được để trống trường này")
    private String password;
}
