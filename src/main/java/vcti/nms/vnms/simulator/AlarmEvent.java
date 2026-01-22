package vcti.nms.vnms.simulator;

import java.time.LocalDateTime;

public class AlarmEvent {

    private String alarmId;
    private String neId;
    private String neType;
    private String severity;
    private String alarmType;
//    private LocalDateTime raisedTime;
    private String raisedTime;

    // getters and setters

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getNeType() {
        return neType;
    }

    public void setNeType(String neType) {
        this.neType = neType;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getRaisedTime() {
        return raisedTime;
    }

    public void setRaisedTime(String raisedTime) {
        this.raisedTime = raisedTime;
    }
}
