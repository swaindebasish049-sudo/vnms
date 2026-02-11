package vcti.nms.vnms.service;

import org.springframework.stereotype.Service;
import vcti.nms.vnms.model.ToolDecision;

@Service
public class AgentService {

    private final OllamaToolPlanner planner;
    private final ToolExecutor executor;

    public AgentService(OllamaToolPlanner planner,
                        ToolExecutor executor) {
        this.planner = planner;
        this.executor = executor;
    }

    public String handlePrompt(String prompt) {

        ToolDecision decision = planner.decideTool(prompt);

        Object result =
                executor.execute(
                        decision.getTool(),
                        decision.getParameters()
                );

        return result.toString();
    }
}
