package vcti.nms.vnms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vcti.nms.vnms.model.Alarm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Optional<Alarm> findByAlarmId(String alarmId);
    List<Alarm> findByStatus(String status);
    List<Alarm> findAllByOrderByRaisedTimeDesc();
    long countBySeverityAndRaisedTimeAfter(String severity, LocalDateTime time);
    List<Alarm> findBySeverityAndRaisedTimeAfter(
            String severity,
            LocalDateTime raisedTime
    );




}
