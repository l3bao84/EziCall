package ATP.Project.EziCall.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotesDTO {

    private String id;
    private String content;
    private String agentName;
    private String notedAt;
}
