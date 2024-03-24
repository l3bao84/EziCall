package ATP.Project.EziCall.service;

import ATP.Project.EziCall.DTO.DetailTicketDTO;
import ATP.Project.EziCall.DTO.NotesDTO;
import ATP.Project.EziCall.DTO.TicketOverviewDTO;
import ATP.Project.EziCall.exception.ObjectNotFoundException;
import ATP.Project.EziCall.exception.TicketModificationNotAllowedException;
import ATP.Project.EziCall.models.*;
import ATP.Project.EziCall.repository.CustomerRepository;
import ATP.Project.EziCall.repository.NoteRepository;
import ATP.Project.EziCall.repository.TicketRepository;
import ATP.Project.EziCall.requests.AddTicketRequest;
import ATP.Project.EziCall.requests.UpdateTicketRequest;
import ATP.Project.EziCall.util.AppConstants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final NoteService noteService;
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final NoteRepository noteRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, NoteService noteService, CustomerRepository customerRepository, UserService userService, NoteRepository noteRepository) {
        this.ticketRepository = ticketRepository;
        this.noteService = noteService;
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.noteRepository = noteRepository;
    }


    public String generateNewId() {

        String prefix = "TK";

        List<String> ticketIds = ticketRepository.getTicketIds();
        long max = ticketIds.stream()
                .map(id -> id.replace(prefix, ""))
                .mapToLong(Long::parseLong)
                .max()
                .orElse(0L);
        return prefix + (String.format("%03d", max + 1));
    }

    @Transactional
    public Ticket createNewTicket(Customer customer, String title, User agent) {

        return Ticket.builder()
                .ticketId(generateNewId())
                .title(title)
                .customer(customer)
                .assignedTo(agent)
                .status(TicketStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .notes(new HashSet<>())
                .build();
    }

    @Transactional
    public TicketOverviewDTO addTicket(AddTicketRequest request) {

        User agent = userService.getUserByUsername();

        Customer customer = customerRepository.findCustomerByPhoneNumber(request.getPhonenumber())
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.CUS_IS_NOT_EXIST));

        Ticket ticket = createNewTicket(customer, request.getTitle(), agent);
        ticket.setStatus(TicketStatus.valueOf(request.getStatus()));
        ticketRepository.save(ticket);

        Note note = noteService.createNewNote(ticket, request.getNotes());
        ticket.getNotes().add(note);
        noteRepository.save(note);

        return ticketRepository.getTicketByTicketId(ticket.getTicketId());
    }

    public DetailTicketDTO getDetailTicket(String id) {
        DetailTicketDTO detailTicketDTO = ticketRepository.getDetailTicket(id)
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.TICKET_IS_NOT_EXIST));

        List<NotesDTO> notesDTOList = noteService.getNotesByTicketId(id);
        for(NotesDTO notesDTO:notesDTOList) {

            LocalDateTime dateTime = LocalDateTime.parse(notesDTO.getNotedAt(), AppConstants.ORIGINAL_FORMATTER);

            notesDTO.setNotedAt(dateTime.format(AppConstants.DATE_TIME_FORMATTER));
        }

        detailTicketDTO.setNotesDTOList(notesDTOList);
        return detailTicketDTO;
    }

    public DetailTicketDTO updateTicket(String id, UpdateTicketRequest updateTicketRequest) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.TICKET_IS_NOT_EXIST));

        if(ticket.getStatus().equals(TicketStatus.CLOSED)) {
            throw new TicketModificationNotAllowedException(AppConstants.TICKET_IS_CLOSED);
        }

        ticket.setTitle(updateTicketRequest.getTitle());
        ticketRepository.save(ticket);

        DetailTicketDTO detailTicketDTO = ticketRepository.getDetailTicket(id).get();
        detailTicketDTO.setNotesDTOList(noteService.getNotesByTicketId(id));

        return detailTicketDTO;
    }

    public TicketOverviewDTO closeTicket(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.TICKET_IS_NOT_EXIST));

        ticket.setStatus(TicketStatus.CLOSED);

        ticketRepository.save(ticket);
        return ticketRepository.getTicketByTicketId(ticket.getTicketId());
    }

    public TicketOverviewDTO openTicket(String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ObjectNotFoundException(AppConstants.AGENT_IS_NOT_EXIST));

        ticket.setStatus(TicketStatus.OPEN);

        ticketRepository.save(ticket);
        return ticketRepository.getTicketByTicketId(ticket.getTicketId());
    }

    public List<TicketOverviewDTO> getTicketsByCustomerId(String id) {
        List<TicketOverviewDTO> ticketOverviewDTOS = ticketRepository.getTicketByCustomerId(id);

        for(TicketOverviewDTO ticketOverviewDTO:ticketOverviewDTOS) {

            LocalDateTime dateTime = LocalDateTime.parse(ticketOverviewDTO.getCreatedAt(), AppConstants.ORIGINAL_FORMATTER);

            ticketOverviewDTO.setCreatedAt(dateTime.format(AppConstants.DATE_TIME_FORMATTER));
        }

        return ticketOverviewDTOS;
    }

    public List<TicketOverviewDTO> getAll() {

        List<TicketOverviewDTO> ticketOverviewDTOS = ticketRepository.getAll();

        for(TicketOverviewDTO ticketOverviewDTO:ticketOverviewDTOS) {

            LocalDateTime dateTime = LocalDateTime.parse(ticketOverviewDTO.getCreatedAt(), AppConstants.ORIGINAL_FORMATTER);

            ticketOverviewDTO.setCreatedAt(dateTime.format(AppConstants.DATE_TIME_FORMATTER));
        }

        return ticketOverviewDTOS;
    }

    public List<TicketOverviewDTO> getTicketByStatus(String status) {
        List<TicketOverviewDTO> ticketOverviewDTOS = ticketRepository.getTicketsByStatus(TicketStatus.valueOf(status.toUpperCase()));

        for(TicketOverviewDTO ticketOverviewDTO:ticketOverviewDTOS) {

            LocalDateTime dateTime = LocalDateTime.parse(ticketOverviewDTO.getCreatedAt(), AppConstants.ORIGINAL_FORMATTER);

            ticketOverviewDTO.setCreatedAt(dateTime.format(AppConstants.DATE_TIME_FORMATTER));
        }

        return ticketOverviewDTOS;
    }
}
