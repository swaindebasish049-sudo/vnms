package vcti.nms.vnms.controller;

import org.springframework.web.bind.annotation.*;
import vcti.nms.vnms.model.Alarm;
import vcti.nms.vnms.service.AlarmService;

import java.util.List;

@RestController
@RequestMapping("/api/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GetMapping
    public List<Alarm> getAllAlarms() {
        return alarmService.getAllAlarms();
    }

    @GetMapping("/active")
    public List<Alarm> getActiveAlarms() {
        return alarmService.getActiveAlarms();
    }

    @PutMapping("/clear/{alarmId}")
    public String clearAlarm(@PathVariable String alarmId) {
        alarmService.clearAlarm(alarmId);
        return "Alarm cleared successfully";
    }

    @PutMapping("/clear-all")
    public String clearAllActiveAlarms() {
        int clearedCount = alarmService.clearAllActiveAlarms();
        return "Cleared " + clearedCount + " active alarms";
    }
}
