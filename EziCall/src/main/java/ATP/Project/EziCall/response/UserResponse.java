package ATP.Project.EziCall.response;

import ATP.Project.EziCall.models.Gender;
import ATP.Project.EziCall.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;

    private String fullname;

    private String username;

    private String password;

    private Role role;

    private String activityStatus;
}
