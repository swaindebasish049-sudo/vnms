package vcti.nms.vnms.simulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlarmProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${vnms.kafka.topic.alarm}")
    private String alarmTopic;

    public AlarmProducer(KafkaTemplate<String, String> kafkaTemplate,
                         ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendAlarm(AlarmEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(alarmTopic, event.getAlarmId(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
