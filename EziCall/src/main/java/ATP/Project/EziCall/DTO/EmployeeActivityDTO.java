package ATP.Project.EziCall.DTO;


import lombok.Data;


@Data
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
