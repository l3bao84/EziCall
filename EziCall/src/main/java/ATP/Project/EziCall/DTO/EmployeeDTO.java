package ATP.Project.EziCall.DTO;

import ATP.Project.EziCall.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private String id;

    private String fullname;

    private String username;

    private String password;

    private Role role;

    private String activityStatus;
}
