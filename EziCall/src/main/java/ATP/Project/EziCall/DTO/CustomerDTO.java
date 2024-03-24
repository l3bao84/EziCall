package ATP.Project.EziCall.DTO;

import ATP.Project.EziCall.models.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CustomerDTO {

    private String customerId;

    private String fullname;

    private String phoneNumber;

    private Gender gender;

    private Long numberCall;
}
