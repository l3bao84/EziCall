package ATP.Project.EziCall.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Họ và tên không được để trống")
    @Pattern(regexp = "^\\p{Lu}\\p{Ll}*(\\s\\p{Lu}\\p{Ll}*)*$", message = "Họ và tên chỉ chứa ký tự là chữ và viết hoa chữ cái đầu")
    private String fullname;

    @NotBlank(message = "Username không được để trống")
    @Pattern(regexp = "\\S{8,15}", message = "Tên đăng nhập từ 8-15 ký tự và không chứa khoảng trắng")
    private String username;

    @NotBlank(message = "Password không được để trống")
    @Pattern(regexp = "\\S{8,}", message = "Mật khẩu có ít nhất 8 ký tự và không chứa khoảng trắng")
    private String password;

    @NotBlank(message = "Không được để trống vai trò")
    private String role;

}
