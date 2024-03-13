package ATP.Project.EziCall.repository;

import ATP.Project.EziCall.models.UserActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserActitityLogRepository extends JpaRepository<UserActivityLog, Long> {

    @Query("SELECT log FROM UserActivityLog log " +
            "WHERE log.user.userId = :userId AND log.status = :status AND log.timestamp " +
            "BETWEEN :startOfDay AND :endOfDay " +
            "ORDER BY log.timestamp DESC")
    UserActivityLog findTopByUserIdAndStatusAndTimestampBetweenOrderByTimestampDesc(Long userId, String status, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
