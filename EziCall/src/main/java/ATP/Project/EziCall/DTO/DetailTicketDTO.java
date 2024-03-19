package ATP.Project.EziCall.DTO;

import ATP.Project.EziCall.models.TicketStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DetailTicketDTO {

    private Long ticketId;
    private String title;
    private TicketStatus status;
    private List<NotesDTO> notesDTOList;

    public DetailTicketDTO(Long ticketId, String title, TicketStatus status) {
        this.ticketId = ticketId;
        this.title = title;
        this.status = status;
    }
}
