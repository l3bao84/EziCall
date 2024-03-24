package ATP.Project.EziCall.DTO;

import ATP.Project.EziCall.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDetailDTO {

    private String id;

    private String fullname;

    private String username;

    private String password;

    private Role role;

}
