package ATP.Project.EziCall.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String firstname;

    private String lastname;

    private String email;

    private String phonenumber;

    private String date;

    private String gender;

    private String activityStatus;
}
