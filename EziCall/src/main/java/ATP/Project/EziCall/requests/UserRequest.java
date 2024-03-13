package ATP.Project.EziCall.requests;

import ATP.Project.EziCall.models.Gender;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class UserRequest {

    @NotBlank(message = "Không được để trống họ")
    private String firstname;

    @NotBlank(message = "Không được để trống tên")
    private String lastname;

    @NotBlank(message = "Không được để trống ngày sinh")
    private String birthDate;

    @NotBlank(message = "Không được để trống giới tính")
    private String gender;

    @NotBlank(message = "Không được để trống email")
    private String email;

    @NotBlank(message = "Không được để trống tên đăng nhập")
    private String username;

    @NotBlank(message = "Không được để trống số điện thoại")
    private String phonenumber;

    @NotBlank(message = "Không được để trống mật khẩu")
    private String password;

}
