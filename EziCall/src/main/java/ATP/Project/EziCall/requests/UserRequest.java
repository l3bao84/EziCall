package ATP.Project.EziCall.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Không được để trống họ và tên")
    @Pattern(regexp = "^[\\p{Lu}\\p{Ll}][\\p{Ll}]*\\s[\\p{Lu}\\p{Ll}][\\p{Ll}]*\\s[\\p{Lu}\\p{Ll}][\\p{Ll}]*$", message = "Tên phải gồm đúng 3 từ, mỗi từ bắt đầu bằng chữ cái viết hoa")
    private String fullname;

    @NotBlank(message = "Không được để trống tên đăng nhập")
    @Pattern(regexp = "\\S{8,15}", message = "Tên đăng nhập từ 8-15 ký tự và không chứa khoảng trắng")
    private String username;

    @NotBlank(message = "Không được để trống mật khẩu")
    @Pattern(regexp = "\\S{8,}", message = "Mật khẩu có ít nhất 8 ký tự và không chứa khoảng trắng")
    private String password;

    @NotBlank(message = "Không được để trống vai trò")
    private String role;

}
