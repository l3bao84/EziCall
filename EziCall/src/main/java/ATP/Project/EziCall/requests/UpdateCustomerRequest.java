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
public class UpdateCustomerRequest implements Validatable {

    @Pattern(regexp = "^\\p{Lu}\\p{Ll}*(\\s\\p{Lu}\\p{Ll}*)*$", message = "Họ và tên chỉ chứa ký tự là chữ và viết hoa chữ cái đầu")
    private String fullname;

    private String gender;

    private String email;

    @NotBlank(message = "SĐT không được để trống")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại chỉ bao gồm 10 chữ số tự nhiên")
    private String phonenumber;

    private String address;
}
