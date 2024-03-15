package ATP.Project.EziCall.response;

import ATP.Project.EziCall.models.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private String customerId;

    private String fullname;

    private String email;

    private String phoneNumber;

    private String address;

    private Gender gender;

    private Long numberCall;
}
