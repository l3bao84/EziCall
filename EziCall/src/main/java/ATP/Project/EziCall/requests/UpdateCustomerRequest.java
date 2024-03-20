package ATP.Project.EziCall.requests;

import ATP.Project.EziCall.util.Validatable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequest implements Validatable {
    private String fullname;

    private String gender;

    private String email;

    @NotBlank(message = "Không được để trống số điện thoại")
    private String phonenumber;

    private String address;
}
