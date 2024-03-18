package ATP.Project.EziCall.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "Không được để trống họ và tên")
    private String fullname;

    @NotBlank(message = "Không được để trống giới tính")
    private String gender;

    @NotBlank(message = "Không được để trống email")
    private String email;

    @NotBlank(message = "Không được để trống số điện thoại")
    private String phonenumber;

    @NotBlank(message = "Không được để trống địa chỉ")
    private String address;

    @NotBlank(message = "Không được để trống tiêu đề")
    private String title;

    @NotBlank(message = "Không được để trống ghi chú")
    private String note;
}
