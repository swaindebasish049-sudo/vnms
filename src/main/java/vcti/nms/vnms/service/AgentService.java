package vcti.nms.vnms.service;

import org.springframework.stereotype.Service;
import vcti.nms.vnms.model.ToolDecision;

@Service
public class AgentService {

    private final OllamaToolPlanner planner;
    private final ToolExecutor executor;
    private final AiReportService aiReportService;

    public AgentService(OllamaToolPlanner planner,
                        ToolExecutor executor,
                        AiReportService aiReportService) {
        this.planner = planner;
        this.executor = executor;
        this.aiReportService = aiReportService;
    }

    public String handlePrompt(String prompt) {

        ToolDecision decision = planner.decideTool(prompt);

        Object result =
                executor.execute(decision.getTool(),
                        decision.getParameters());

        return "Total critical count is " + result.toString();
    }
}
