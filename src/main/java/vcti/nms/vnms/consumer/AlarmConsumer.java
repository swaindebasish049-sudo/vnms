package vcti.nms.vnms.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vcti.nms.vnms.model.Alarm;
import vcti.nms.vnms.repository.AlarmRepository;

import java.time.LocalDateTime;

@Service
public class AlarmConsumer {

    private final AlarmRepository alarmRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AlarmConsumer(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @KafkaListener(
            topics = "${vnms.kafka.topic.alarm}",
            groupId = "alarm-consumer-group"
    )
    public void consume(String message) {
        try {
            // Convert JSON → Alarm object
            Alarm alarm = objectMapper.readValue(message, Alarm.class);

            // Set NMS-specific fields
            alarm.setStatus("ACTIVE");
            alarm.setRaisedTime(java.time.LocalDateTime.now().toString());

            // Save to DB
            alarmRepository.save(alarm);

            System.out.println("✅ Alarm saved to DB: " + alarm.getAlarmType());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
