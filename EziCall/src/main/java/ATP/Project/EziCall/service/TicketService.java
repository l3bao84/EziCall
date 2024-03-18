package ATP.Project.EziCall.service;

import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.models.*;
import ATP.Project.EziCall.repository.CustomerRepository;
import ATP.Project.EziCall.repository.TicketRepository;
import ATP.Project.EziCall.requests.AddTicketRequest;
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
    public Ticket createNewTicket(Customer customer, String title, String content, User agent) {

        Ticket ticket = Ticket.builder()
                .title(title)
                .customer(customer)
                .assignedTo(agent)
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .notes(new HashSet<>())
                .build();

        Note note = noteService.createNewNote(ticket, content);

        ticket.getNotes().add(note);

        return ticket;
    }

    @Transactional
    public Ticket addTicket(AddTicketRequest request) {

        User agent = userService.getUserByUsername();

        Customer customer = customerRepository.findCustomerByPhoneNumber(request.getPhonenumber())
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại khách hàng có sđt: " + request.getPhonenumber()));

        Ticket ticket = createNewTicket(customer, request.getTitle(), request.getNotes(), agent);

        return ticketRepository.save(ticket);
    }

    public Ticket closeTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ObjectNotFoundException("Không tồn tại ticket có id: " + ticketId));

        ticket.setStatus(TicketStatus.CLOSED);

        ticketRepository.save(ticket);
        return ticket;
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
