package vcti.nms.vnms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vcti.nms.vnms.model.ToolDecision;

import java.util.HashMap;
import java.util.Map;

@Service
public class OllamaToolPlanner {

    private static final String OLLAMA_URL =
            "http://localhost:11434/api/generate";

    private final ObjectMapper mapper = new ObjectMapper();

    public ToolDecision decideTool(String userPrompt) {

        RestTemplate restTemplate = new RestTemplate();

        String systemPrompt = """
       You are an AI tool selector for a Telecom NMS.
       
       You MUST return ONLY valid JSON.
       DO NOT explain.
       DO NOT add text.
       DO NOT add markdown.
       
       Available tool:
       - getCriticalAlarmCount(hours)
       
       JSON format:
       {
         "tool": "getCriticalAlarmCount",
         "parameters": {
           "hours": number
         }
       }
       
       
       Do NOT include "result".
       Do NOT include any extra fields.

        User Prompt: %s
       """.formatted(userPrompt);

        Map<String, Object> req = new HashMap<>();
        req.put("model", "llama3");
        req.put("prompt", systemPrompt);
        req.put("stream", false);

        Map res =
                restTemplate.postForObject(OLLAMA_URL, req, Map.class);

        try {
            String raw = res.get("response").toString();
            int start = raw.indexOf("{");
            int end = raw.lastIndexOf("}");

            String jsonOnly = raw.substring(start, end + 1);

            return mapper.readValue(jsonOnly, ToolDecision.class);

        } catch (Exception e) {
            throw new RuntimeException("Ollama returned invalid JSON");
        }
    }
}
