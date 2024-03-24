package ATP.Project.EziCall.DTO;

import ATP.Project.EziCall.models.TicketStatus;
import lombok.Data;

import java.util.List;

@Data
public class DetailTicketDTO {

    private String ticketId;
    private String title;
    private TicketStatus status;
    private List<NotesDTO> notesDTOList;

    public DetailTicketDTO(String ticketId, String title, TicketStatus status) {
        this.ticketId = ticketId;
        this.title = title;
        this.status = status;
    }
}
