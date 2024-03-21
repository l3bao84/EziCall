package ATP.Project.EziCall.DTO;

import ATP.Project.EziCall.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private String id;
    private String fullname;
    private Role role;
}
