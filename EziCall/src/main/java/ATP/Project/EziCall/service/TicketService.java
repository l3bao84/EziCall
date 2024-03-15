package ATP.Project.EziCall.service;

import ATP.Project.EziCall.exception.UserNotFoundException;
import ATP.Project.EziCall.models.Customer;
import ATP.Project.EziCall.models.Ticket;
import ATP.Project.EziCall.models.TicketStatus;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.repository.CustomerRepository;
import ATP.Project.EziCall.repository.TicketRepository;
import ATP.Project.EziCall.repository.UserRepository;
import ATP.Project.EziCall.requests.AddTicketRequest;
import ATP.Project.EziCall.response.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Ticket addNewTicket(AddTicketRequest request) {

        User emp = userRepository.findByUsername(userService.getAuthenticatedUsername()).get();

        CustomerResponse customer = customerRepository.findByPhone(request.getPhonenumber())
                .orElseThrow(() -> new UserNotFoundException("Không tồn tại khách hàng có sđt: " + request.getPhonenumber()));


        Ticket ticket = Ticket.builder()
                .customer(customerRepository.findById(customer.getCustomerId()).get())
                .assignedTo(emp)
                .employeeNotes(request.getNotes())
                .status(TicketStatus.valueOf(request.getStatus()))
                .createdAt(LocalDateTime.now())
                .build();

        return ticketRepository.save(ticket);
    }

}
