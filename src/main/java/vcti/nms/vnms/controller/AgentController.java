package vcti.nms.vnms.controller;

import org.springframework.web.bind.annotation.*;
import vcti.nms.vnms.model.AgentRequest;
import vcti.nms.vnms.model.AgentResponse;
import vcti.nms.vnms.service.AgentService;
import vcti.nms.vnms.service.AlarmService;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final AgentService agentService;
    private final AlarmService alarmService;

    public AgentController(AgentService agentService, AlarmService alarmService) {
        this.agentService = agentService;
        this.alarmService = alarmService;
    }

    @PostMapping("/chat")
    public AgentResponse chat(@RequestBody AgentRequest request) {

        String reply =
                agentService.handlePrompt(request.getPrompt());

        return new AgentResponse(reply);
    }

    @GetMapping("/critical/count")
    public long getCriticalCount(@RequestParam(defaultValue = "1") int hours) {
        return alarmService.countCriticalLastHours(hours);
    }

}
