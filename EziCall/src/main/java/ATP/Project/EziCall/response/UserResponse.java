package ATP.Project.EziCall.response;

import ATP.Project.EziCall.models.Gender;
import ATP.Project.EziCall.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;

    private String firstname;

    private String lastname;

    private String email;

    private String phonenumber;

    private LocalDate date;

    private Gender gender;

    private String activityStatus;

    private String username;

    private String password;

    private Role role;
}
