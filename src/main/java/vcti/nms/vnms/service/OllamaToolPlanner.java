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
       You are an AI tool selector for VNMS (Telecom NMS).
       You can call tools multiple times to complete a task.
       
       You are a STRICT JSON API.
       
       You MUST return ONLY valid JSON.
       You MUST NOT add text.
       You MUST NOT add explanation.
       You MUST NOT add markdown.
       You MUST NOT add comments.
       
       
       Available tools:
       
       1) getCriticalAlarmCount(hours)
       2) generateAlarmPdf(hours)
       3) sendEmail(to, subject, body)
       
       Rules:
       - Choose the most appropriate tool
       - Extract parameters from user input
       - Respond with ONE tool only
       - Return ONLY this format
       
       JSON format:
       {
       "tool": "<tool_name>",
       "parameters": {
       "<key>": <value>
        }
       }
       
       If you output anything else, it is considered invalid.
       
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
