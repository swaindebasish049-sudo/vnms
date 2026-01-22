package vcti.nms.vnms.simulator;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@EnableScheduling
public class AlarmSimulator {

    private final AlarmProducer alarmProducer;

    private static final String[] SEVERITIES = {
            "CRITICAL", "MAJOR", "MINOR", "WARNING"
    };

    private static final String[] ALARM_TYPES = {
            "LOS", "LINK_DOWN", "HIGH_CPU"
    };

    private static final String[] NE_TYPES = {
            "OTN", "ROUTER", "SWITCH"
    };

    public AlarmSimulator(AlarmProducer alarmProducer) {
        this.alarmProducer = alarmProducer;
    }

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void generateAlarm() {
        AlarmEvent event = new AlarmEvent();
        event.setAlarmId(UUID.randomUUID().toString());
        event.setNeId("NE-" + (int)(Math.random() * 100));
        event.setNeType(randomFrom(NE_TYPES));
        event.setSeverity(randomFrom(SEVERITIES));
        event.setAlarmType(randomFrom(ALARM_TYPES));
        event.setRaisedTime(LocalDateTime.now().toString());
        ;

        alarmProducer.sendAlarm(event);

        System.out.println("📡 Alarm sent: " + event.getAlarmType()
                + " | " + event.getSeverity());
    }

    private String randomFrom(String[] values) {
        return values[(int)(Math.random() * values.length)];
    }
}
