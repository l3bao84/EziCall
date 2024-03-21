package ATP.Project.EziCall.DTO;

import ATP.Project.EziCall.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EmployeeActivityDTO {

    private String id;
    private String fullname;
    private String activityStatus;
    private String activityTime;

    public EmployeeActivityDTO(String id, String fullname, String activityStatus) {
        this.id = id;
        this.fullname = fullname;
        this.activityStatus = activityStatus;
    }
}
