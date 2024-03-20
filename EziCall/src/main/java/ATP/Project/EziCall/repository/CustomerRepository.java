package ATP.Project.EziCall.repository;

import ATP.Project.EziCall.DTO.CallHistoryDTO;
import ATP.Project.EziCall.DTO.CallHistoryDetailsDTO;
import ATP.Project.EziCall.DTO.CustomerDTO;
import ATP.Project.EziCall.models.Customer;
import ATP.Project.EziCall.models.Gender;
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

    @Query("SELECT new ATP.Project.EziCall.DTO.CustomerDTO(c.customerId, c.fullname, c.email, c.phoneNumber, c.address, c.gender, (" +
            "SELECT COUNT(t.ticketId) FROM Ticket t " +
            "WHERE t.customer.customerId = c.customerId " +
            "GROUP BY t.customer.customerId)) " +
            "FROM Customer c " +
            "WHERE (:phone IS NULL OR :phone = '' OR c.phoneNumber LIKE %:phone%) AND " +
            "(:name IS NULL OR :name = '' OR c.fullname LIKE %:name%)")
    List<CustomerDTO> findCustomer(@Param("phone") String phoneNumber, @Param("name") String name);

    @Query("SELECT new ATP.Project.EziCall.DTO.CustomerDTO(c.customerId, c.fullname, c.email, c.phoneNumber, c.address, c.gender, (" +
            "SELECT COUNT(t.ticketId) FROM Ticket t " +
            "WHERE t.customer.customerId = c.customerId " +
            "GROUP BY t.customer.customerId)) " +
            "FROM Customer c " +
            "WHERE (:phone IS NULL OR :phone = '' OR c.phoneNumber LIKE %:phone%) AND " +
            "(:name IS NULL OR :name = '' OR c.fullname LIKE %:name%) AND " +
            "(:id IS NULL OR :id = '' OR c.customerId = :id) AND " +
            "(:gender IS NULL OR :gender = '' OR c.gender = :gender)")
    List<CustomerDTO> findCustomer(@Param("phone") String phoneNumber, @Param("name") String name, @Param("id") String id, @Param("gender") Gender gender);

    @Query("SELECT new ATP.Project.EziCall.DTO.CustomerDTO(c.customerId, c.fullname, c.email, c.phoneNumber, c.address, c.gender, " +
            "(SELECT count(t) FROM Ticket t WHERE t.customer.customerId = c.customerId GROUP BY t.customer.customerId))" +
            "FROM Customer c")
    List<CustomerDTO> getAll();

    @Query("SELECT new ATP.Project.EziCall.DTO.CustomerDTO(c.customerId, c.fullname, c.email, c.phoneNumber, c.address, c.gender, " +
            "(SELECT count(t) FROM Ticket t WHERE t.customer.customerId = c.customerId GROUP BY t.customer.customerId))" +
            "FROM Customer c WHERE c.customerId = :id")
    Optional<CustomerDTO> getCustomerById(@Param("id") String id);

    @Query("SELECT new ATP.Project.EziCall.DTO.CallHistoryDTO(c.customerId, c.phoneNumber, c.fullname, " +
            "(SELECT n.notedAt FROM Note n JOIN Ticket t ON n.ticket.ticketId = t.ticketId WHERE t.customer.customerId = c.customerId ORDER BY n.notedAt DESC LIMIT 1) , " +
            "(SELECT COUNT(t) FROM Ticket t WHERE t.customer.customerId = c.customerId AND t.status = 'OPEN')) " +
            "FROM Customer c")
    List<CallHistoryDTO> getCallHistory();

    @Query("SELECT new ATP.Project.EziCall.DTO.CallHistoryDetailsDTO(c.customerId, c.phoneNumber, c.fullname, c.gender, c.email, c.address) " +
            "FROM Customer c WHERE c.customerId = :id")
    Optional<CallHistoryDetailsDTO> getCallHistoryDetails(@Param("id") String customerId);
}
