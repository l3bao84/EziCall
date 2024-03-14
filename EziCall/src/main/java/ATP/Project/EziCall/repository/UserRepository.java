package ATP.Project.EziCall.repository;

import ATP.Project.EziCall.models.Role;
import ATP.Project.EziCall.models.User;
import ATP.Project.EziCall.response.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT new ATP.Project.EziCall.response.UserResponse(u.userId, u.firstname, u.lastname, u.email, u.phonenumber, u.birthDate, u.gender, " +
            "(SELECT a.status FROM UserActivityLog a JOIN User u ON a.user.userId = u.userId " +
            "WHERE a.user.userId = u.userId AND FUNCTION('DATE', a.timestamp) = FUNCTION('DATE', CURRENT_TIMESTAMP) ORDER BY a.timestamp DESC LIMIT 1),u.username, u.password, u.role) " +
            "FROM User u WHERE u.userId = :id")
    Optional<UserResponse> getEmployee(@Param("id") long id);

    @Query("SELECT new ATP.Project.EziCall.response.UserResponse(u.userId, u.firstname, u.lastname, u.email, u.phonenumber, u.birthDate, u.gender, " +
            "(SELECT a.status FROM UserActivityLog a JOIN User u ON a.user.userId = u.userId " +
            "WHERE a.user.userId = u.userId AND FUNCTION('DATE', a.timestamp) = FUNCTION('DATE', CURRENT_TIMESTAMP) ORDER BY a.timestamp DESC LIMIT 1),u.username, u.password, u.role) " +
            "FROM User u WHERE u.role = :role")
    List<UserResponse> getEmployees(@Param("role") Role role);

    @Query("SELECT new ATP.Project.EziCall.response.UserResponse(u.userId, u.firstname, u.lastname, u.email, u.phonenumber, u.birthDate, u.gender, " +
            "(SELECT a.status FROM UserActivityLog a JOIN User u ON a.user.userId = u.userId " +
            "WHERE a.user.userId = u.userId AND FUNCTION('DATE', a.timestamp) = FUNCTION('DATE', CURRENT_TIMESTAMP) ORDER BY a.timestamp DESC LIMIT 1),u.username, u.password, u.role) " +
            "FROM User u WHERE (u.firstname LIKE %:keyword% OR u.lastname LIKE %:keyword%) AND u.role = :role")
    List<UserResponse> findEmployeeByName(@Param("keyword") String name, @Param("role") Role role);

    @Query("SELECT new ATP.Project.EziCall.response.UserResponse(u.userId, u.firstname, u.lastname, u.email, u.phonenumber, u.birthDate, u.gender, " +
            "(SELECT a.status FROM UserActivityLog a JOIN User u ON a.user.userId = u.userId " +
            "WHERE a.user.userId = u.userId AND FUNCTION('DATE', a.timestamp) = FUNCTION('DATE', CURRENT_TIMESTAMP) ORDER BY a.timestamp DESC LIMIT 1),u.username, u.password, u.role)" +
            "FROM User u JOIN UserActivityLog a ON u.userId = a.user.userId " +
            "WHERE a.status = 'ONLINE' AND FUNCTION('DATE', a.timestamp) = FUNCTION('DATE', CURRENT_TIMESTAMP)")
    List<UserResponse> findEmployeeOnline();
}
