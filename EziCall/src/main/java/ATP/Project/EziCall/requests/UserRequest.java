package ATP.Project.EziCall.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Không được để trống họ")
    private String fullname;

    @NotBlank(message = "Không được để trống tên đăng nhập")
    private String username;

    @NotBlank(message = "Không được để trống mật khẩu")
    private String password;

    @NotBlank(message = "Không được để trống vai trò")
    private String role;

}
