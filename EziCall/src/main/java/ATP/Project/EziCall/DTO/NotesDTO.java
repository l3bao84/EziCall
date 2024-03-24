package ATP.Project.EziCall.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotesDTO {

    private String id;
    private String content;
    private String agentName;
    private String notedAt;
}
