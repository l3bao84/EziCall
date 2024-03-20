package ATP.Project.EziCall.service;

import ATP.Project.EziCall.DTO.DetailTicketDTO;
import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.models.*;
import ATP.Project.EziCall.repository.CustomerRepository;
import ATP.Project.EziCall.repository.TicketRepository;
import ATP.Project.EziCall.requests.AddTicketRequest;
import ATP.Project.EziCall.requests.UpdateTicketRequest;
import ATP.Project.EziCall.response.TicketResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private NoteService noteService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Ticket createNewTicket(Customer customer, String title, User agent) {

        Ticket ticket = Ticket.builder()
                .title(title)
                .customer(customer)
                .assignedTo(agent)
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .notes(new HashSet<>())
                .build();

        return ticket;
    }

    @Transactional
    public TicketResponse addTicket(AddTicketRequest request) {

        User agent = userService.getUserByUsername();

        Customer customer = customerRepository.findCustomerByPhoneNumber(request.getPhonenumber())
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại khách hàng có sđt: " + request.getPhonenumber()));

        Ticket ticket = createNewTicket(customer, request.getTitle(), agent);
        ticket.setStatus(TicketStatus.valueOf(request.getStatus()));
        ticketRepository.save(ticket);

        Note note = noteService.createNewNote(ticket, request.getNotes());
        ticket.getNotes().add(note);

        return ticketRepository.getTicketByTicketId(ticket.getTicketId());
    }

    public DetailTicketDTO getDetailTicket(String id) {
        DetailTicketDTO detailTicketDTO = ticketRepository.getDetailTicket(id)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại ticket có id: " + id));

        detailTicketDTO.setNotesDTOList(noteService.getNotesByTicketId(id));
        return detailTicketDTO;
    }

    public DetailTicketDTO updateTicket(String id, UpdateTicketRequest updateTicketRequest) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại ticket có id: " + id));

        ticket.setTitle(updateTicketRequest.getTitle());
        ticketRepository.save(ticket);

        DetailTicketDTO detailTicketDTO = ticketRepository.getDetailTicket(id).get();
        detailTicketDTO.setNotesDTOList(noteService.getNotesByTicketId(id));

        return detailTicketDTO;
    }

    public TicketResponse closeTicket(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại ticket có id: " + ticketId));

        ticket.setStatus(TicketStatus.CLOSED);

        ticketRepository.save(ticket);
        return ticketRepository.getTicketByTicketId(ticket.getTicketId());
    }

    public List<TicketResponse> getTicketsByCustomerId(String id) {
        return ticketRepository.getTicketByCustomerId(id);
    }

    public List<TicketResponse> getAll() {
        return ticketRepository.getAll();
    }

    public List<TicketResponse> getTicketByStatus(String status) {
        return ticketRepository.getTicketsByStatus(TicketStatus.valueOf(status.toUpperCase()));
    }
}
