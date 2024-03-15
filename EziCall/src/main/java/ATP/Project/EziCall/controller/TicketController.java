package ATP.Project.EziCall.controller;

import ATP.Project.EziCall.requests.AddTicketRequest;
import ATP.Project.EziCall.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping()
    public ResponseEntity<?> addNewTicket(@Valid @RequestBody AddTicketRequest request,
                                          BindingResult result) {
        if(result.hasErrors()) {
            return ResponseEntity.ok().body("");
        }
        return ResponseEntity.ok().body("");
    }
}
