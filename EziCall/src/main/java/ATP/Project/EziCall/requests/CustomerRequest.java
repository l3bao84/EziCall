package ATP.Project.EziCall.requests;

import ATP.Project.EziCall.util.Validatable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest implements Validatable {

    @Pattern(regexp = "^[\\p{Lu}\\p{Ll}][\\p{Ll}]*\\s[\\p{Lu}\\p{Ll}][\\p{Ll}]*\\s[\\p{Lu}\\p{Ll}][\\p{Ll}]*$", message = "Tên phải gồm đúng 3 từ, mỗi từ bắt đầu bằng chữ cái viết hoa")
    private String fullname;

    private String gender;

    private String email;

    @NotBlank(message = "Không được để trống số điện thoại")
    private String phonenumber;

    private String address;

    @NotBlank(message = "Không được để trống tiêu đề")
    private String title;

    private String note;
}
