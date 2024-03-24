package ATP.Project.EziCall.repository;

import ATP.Project.EziCall.DTO.EmployeeActivityDTO;
import ATP.Project.EziCall.DTO.EmployeeDTO;
import ATP.Project.EziCall.models.Role;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.DTO.EmployeeDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("SELECT new ATP.Project.EziCall.DTO.EmployeeDetailDTO(u.userId, u.fullname, u.username, u.password, u.role) " +
            "FROM User u WHERE u.userId = :id")
    Optional<EmployeeDetailDTO> getEmployee(@Param("id") String id);

    @Query("SELECT new ATP.Project.EziCall.DTO.EmployeeDTO(u.userId, u.fullname, u.role) " +
            "FROM User u")
    List<EmployeeDTO> getEmployees();

    @Query("SELECT new ATP.Project.EziCall.DTO.EmployeeDTO(u.userId, u.fullname, u.role) " +
            "FROM User u WHERE u.role = :role")
    List<EmployeeDTO> filterUserByRole(@Param("role") Role role);

    @Query("SELECT new ATP.Project.EziCall.DTO.EmployeeDTO(u.userId, u.fullname, u.role) " +
            "FROM User u " +
            "WHERE (:name IS NULL OR :name = '' OR u.fullname LIKE %:name%) AND " +
            "(:username IS NULL OR :username = '' OR u.username LIKE %:username%) AND " +
            "(:role IS NULL OR :role = '' OR u.role = :role) AND " +
            "(:id IS NULL OR :id = '' OR u.userId LIKE %:id%)")
    List<EmployeeDTO> findEmployee(@Param("name") String name, @Param("username") String username, @Param("role") Role role, @Param("id") String id);

    @Query("SELECT new ATP.Project.EziCall.DTO.EmployeeActivityDTO(u.userId, u.fullname, " +
            "(SELECT CONCAT(a.status, ' ', a.timestamp) FROM UserActivityLog a WHERE a.user.userId = u.userId " +
            "AND (:status IS NULL OR :status = '' OR a.status = :status) " +
            "ORDER BY a.timestamp DESC LIMIT 1)) " +
            "FROM User u WHERE :status IS NULL OR :status = '' OR u.userId IN " +
            "(SELECT a.user.userId FROM UserActivityLog a WHERE a.status = :status)")
    List<EmployeeActivityDTO> getEmployeesActivities(@Param("status") String status);


}
