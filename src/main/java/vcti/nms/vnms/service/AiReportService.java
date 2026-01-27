package vcti.nms.vnms.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiReportService {

    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public String generateIncidentReport(long criticalCount) {

        RestTemplate restTemplate = new RestTemplate();

        // Prompt for AI
        String prompt = """
                You are an NMS Fault Management AI.
                VNMS detected an alarm storm.

                Total CRITICAL alarms in last 1 hour: %d

                Generate a short telecom incident report with:
                1. Summary
                2. Possible root cause
                3. Recommended next action
                """.formatted(criticalCount);

        // Request body
        Map<String, Object> request = new HashMap<>();
        request.put("model", "llama3");
        request.put("prompt", prompt);
        request.put("stream", false);

        // Call Ollama
        Map response = restTemplate.postForObject(OLLAMA_URL, request, Map.class);

        return response.get("response").toString();
    }
}
