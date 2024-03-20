package ATP.Project.EziCall.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Không được để trống họ")
    @Pattern(regexp = "^[A-Z][a-z]*\\s[A-Z][a-z]*\\s[A-Z][a-z]*$", message = "Tên phải gồm đúng 3 từ, mỗi từ bắt đầu bằng chữ cái viết hoa")
    private String fullname;

    @NotBlank(message = "Không được để trống tên đăng nhập")
    private String username;

    @NotBlank(message = "Không được để trống mật khẩu")
    private String password;

    @NotBlank(message = "Không được để trống vai trò")
    private String role;

}
