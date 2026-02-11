package vcti.nms.vnms.controller;

import org.springframework.web.bind.annotation.*;
import vcti.nms.vnms.model.OpenAIRequest;
import vcti.nms.vnms.model.OpenAIResponse;
import vcti.nms.vnms.service.AgentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class OpenAiCompatibleController {

    private final AgentService agentService;

    public OpenAiCompatibleController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping("/models")
    public Map<String, Object> models() {

        Map<String, Object> model = new HashMap<>();
        model.put("id", "vnms-agent");
        model.put("object", "model");
        model.put("owned_by", "vnms");

        Map<String, Object> response = new HashMap<>();
        response.put("object", "list");
        response.put("data", List.of(model));

        return response;
    }


    @PostMapping("/chat/completions")
    public OpenAIResponse chat(@RequestBody OpenAIRequest request) {

        String userPrompt = request.getMessages().stream()
                .filter(m -> "user".equals(m.getRole()))
                .reduce((first, second) -> second)
                .map(OpenAIRequest.Message::getContent)
                .orElse("");

        String result = agentService.handlePrompt(userPrompt);

        return new OpenAIResponse(result);
    }

}

