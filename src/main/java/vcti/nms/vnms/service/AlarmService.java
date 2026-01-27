package vcti.nms.vnms.service;

import org.springframework.stereotype.Service;
import vcti.nms.vnms.model.Alarm;
import vcti.nms.vnms.repository.AlarmRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    // All alarms (history)
    public List<Alarm> getAllAlarms() {
        return alarmRepository.findAllByOrderByRaisedTimeDesc();
    }

    // Active alarms only
    public List<Alarm> getActiveAlarms() {
        return alarmRepository.findAll()
                .stream()
                .filter(alarm -> "ACTIVE".equals(alarm.getStatus()))
                .toList();
    }

    public void clearAlarm(String alarmId) {

        alarmRepository.findByAlarmId(alarmId).ifPresent(alarm -> {
            alarm.setStatus("CLEARED");
            alarm.setClearedTime(LocalDateTime.now());
            alarmRepository.save(alarm);
        });
    }

    public int clearAllActiveAlarms() {

        List<Alarm> activeAlarms = alarmRepository.findByStatus("ACTIVE");

        for (Alarm alarm : activeAlarms) {
            alarm.setStatus("CLEARED");
            alarm.setClearedTime(LocalDateTime.now());
        }

        alarmRepository.saveAll(activeAlarms);

        return activeAlarms.size();
    }


}
