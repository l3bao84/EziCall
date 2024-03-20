package ATP.Project.EziCall.repository;

import ATP.Project.EziCall.models.Role;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.DTO.EmployeeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("SELECT new ATP.Project.EziCall.DTO.EmployeeDTO(u.userId, u.fullname, u.username, u.password, u.role, " +
            "(SELECT a.status FROM UserActivityLog a " +
            "WHERE a.user.userId = u.userId AND FUNCTION('DATE', a.timestamp) = FUNCTION('DATE', CURRENT_TIMESTAMP) ORDER BY a.timestamp DESC LIMIT 1)) " +
            "FROM User u WHERE u.userId = :id")
    Optional<EmployeeDTO> getEmployee(@Param("id") String id);

    @Query("SELECT new ATP.Project.EziCall.DTO.EmployeeDTO(u.userId, u.fullname, u.username, u.password, u.role," +
            "(SELECT a.status FROM UserActivityLog a " +
            "WHERE a.user.userId = u.userId AND FUNCTION('DATE', a.timestamp) = FUNCTION('DATE', CURRENT_TIMESTAMP) ORDER BY a.timestamp DESC LIMIT 1)) " +
            "FROM User u")
    List<EmployeeDTO> getEmployees();

    @Query("SELECT new ATP.Project.EziCall.DTO.EmployeeDTO(u.userId, u.fullname, u.username, u.password, u.role," +
            "(SELECT a.status FROM UserActivityLog a " +
            "WHERE a.user.userId = u.userId AND FUNCTION('DATE', a.timestamp) = FUNCTION('DATE', CURRENT_TIMESTAMP) ORDER BY a.timestamp DESC LIMIT 1)) " +
            "FROM User u WHERE u.role = :role")
    List<EmployeeDTO> filterUserByRole(@Param("role") Role role);

    @Query("SELECT new ATP.Project.EziCall.DTO.EmployeeDTO(u.userId, u.fullname, u.username, u.password, u.role," +
            "(SELECT a.status FROM UserActivityLog a " +
            "WHERE a.user.userId = u.userId AND FUNCTION('DATE', a.timestamp) = FUNCTION('DATE', CURRENT_TIMESTAMP) ORDER BY a.timestamp DESC LIMIT 1)) " +
            "FROM User u " +
            "WHERE (:name IS NULL OR :name = '' OR u.fullname LIKE %:name%) AND " +
            "(:username IS NULL OR :username = '' OR u.username LIKE %:username%) AND " +
            "(:role IS NULL OR :role = '' OR u.role = :role) AND " +
            "(:id IS NULL OR :id = '' OR u.userId LIKE %:id%)")
    List<EmployeeDTO> findEmployee(@Param("name") String name, @Param("username") String username,@Param("role") Role role, @Param("id") String id);

    @Query("SELECT new ATP.Project.EziCall.DTO.EmployeeDTO(u.userId, u.fullname, u.username, u.password, u.role," +
            "(SELECT CONCAT(a.status, ' ', a.timestamp) FROM UserActivityLog a " +
            "WHERE a.user.userId = u.userId AND FUNCTION('DATE', a.timestamp) = FUNCTION('DATE', CURRENT_TIMESTAMP) ORDER BY a.timestamp DESC LIMIT 1))" +
            "FROM User u JOIN UserActivityLog a ON u.userId = a.user.userId " +
            "WHERE a.status = 'ONLINE' AND FUNCTION('DATE', a.timestamp) = FUNCTION('DATE', CURRENT_TIMESTAMP)")
    List<EmployeeDTO> findEmployeeOnline();


}
