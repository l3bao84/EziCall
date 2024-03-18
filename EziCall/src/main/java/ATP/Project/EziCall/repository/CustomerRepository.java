package ATP.Project.EziCall.repository;

import ATP.Project.EziCall.DTO.CallHistoryDTO;
import ATP.Project.EziCall.DTO.CallHistoryDetailsDTO;
import ATP.Project.EziCall.models.Customer;
import ATP.Project.EziCall.response.CustomerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findCustomerByPhoneNumber(String phonenumber);

    @Query("SELECT new ATP.Project.EziCall.response.CustomerResponse(c.customerId, c.fullname, c.email, c.phoneNumber, c.address, c.gender, " +
            "(SELECT count(t) FROM Ticket t WHERE t.customer.customerId = c.customerId GROUP BY t.customer.customerId))" +
            "FROM Customer c WHERE c.phoneNumber = :phone")
    Optional<CustomerResponse> findByPhone(@Param("phone") String phoneNumber);


    @Query("SELECT new ATP.Project.EziCall.response.CustomerResponse(c.customerId, c.fullname, c.email, c.phoneNumber, c.address, c.gender, " +
            "(SELECT count(t) FROM Ticket t WHERE t.customer.customerId = c.customerId GROUP BY t.customer.customerId))" +
            "FROM Customer c")
    List<CustomerResponse> getAll();

    @Query("SELECT new ATP.Project.EziCall.response.CustomerResponse(c.customerId, c.fullname, c.email, c.phoneNumber, c.address, c.gender, " +
            "(SELECT count(t) FROM Ticket t WHERE t.customer.customerId = c.customerId GROUP BY t.customer.customerId))" +
            "FROM Customer c WHERE c.customerId = :id")
    Optional<CustomerResponse> getCustomerById(@Param("id") String id);

    @Query("SELECT new ATP.Project.EziCall.DTO.CallHistoryDTO(c.customerId, c.phoneNumber, c.fullname, " +
            "(SELECT n.notedAt FROM Note n JOIN Ticket t ON n.ticket.ticketId = t.ticketId WHERE t.customer.customerId = c.customerId ORDER BY n.notedAt DESC LIMIT 1) , " +
            "(SELECT COUNT(t) FROM Ticket t WHERE t.customer.customerId = c.customerId AND t.status = 'OPEN')) " +
            "FROM Customer c")
    List<CallHistoryDTO> getCallHistory();

    @Query("SELECT new ATP.Project.EziCall.DTO.CallHistoryDetailsDTO(c.customerId, c.phoneNumber, c.fullname, c.gender, c.email, c.address) " +
            "FROM Customer c WHERE c.customerId = :id")
    Optional<CallHistoryDetailsDTO> getCallHistoryDetails(@Param("id") String customerId);
}
