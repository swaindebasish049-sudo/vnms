package vcti.nms.vnms.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ToolExecutor {

    private final RestTemplate restTemplate = new RestTemplate();

    public Object execute(String tool, Map<String, Object> params) {

        if (tool.equals("getCriticalAlarmCount")) {

            int hours = (int) params.getOrDefault("hours", 1);

            String url =
                    "http://localhost:8080/api/agent/critical/count?hours="
                            + hours;

            return restTemplate.getForObject(url, Long.class);
        }

        if (tool.equals("generateAlarmPdf")) {

            int hours = (int) params.getOrDefault("hours", 1);

            String url =
                    "http://localhost:8080/api/reports/critical/pdf?hours="
                            + hours;

            return restTemplate.getForObject(url, String.class);
        }

        if (tool.equals("sendEmail")) {

            String url = "http://localhost:8080/api/notifications/email";

            return restTemplate.postForObject(url, params, String.class);
        }


        return "Unknown tool";
    }
}

