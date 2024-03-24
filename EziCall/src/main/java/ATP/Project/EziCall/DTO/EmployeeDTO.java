package ATP.Project.EziCall.DTO;

import ATP.Project.EziCall.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {

    private String id;
    private String fullname;
    private Role role;
}
