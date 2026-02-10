package vcti.nms.vnms.model;

import java.util.Map;

public class ToolDecision {

    private String tool;
    private Map<String, Object> parameters;

    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
