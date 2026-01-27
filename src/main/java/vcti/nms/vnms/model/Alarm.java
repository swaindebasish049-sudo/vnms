package vcti.nms.vnms.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alarms")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alarm_id", nullable = false)
    private String alarmId;

    @Column(name = "ne_id")
    private String neId;

    @Column(name = "ne_type")
    private String neType;

    @Column(name = "severity")
    private String severity;   // CRITICAL, MAJOR, MINOR, WARNING

    @Column(name = "alarm_type")
    private String alarmType;  // LOS, LINK_DOWN, HIGH_CPU

    @Column(name = "status")
    private String status;     // ACTIVE, CLEARED

    @Column(name = "raised_time")
    private LocalDateTime raisedTime;

    @Column(name = "cleared_time")
    private LocalDateTime clearedTime;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRaisedTime() {
        return raisedTime;
    }

    public void setRaisedTime(LocalDateTime raisedTime) {
        this.raisedTime = raisedTime;
    }

    public LocalDateTime getClearedTime() {
        return clearedTime;
    }

    public void setClearedTime(LocalDateTime clearedTime) {
        this.clearedTime = clearedTime;
    }
}
