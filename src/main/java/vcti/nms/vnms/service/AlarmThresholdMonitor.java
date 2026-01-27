package vcti.nms.vnms.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vcti.nms.vnms.repository.AlarmRepository;

import java.time.LocalDateTime;

@Service
public class AlarmThresholdMonitor {

    private final AlarmRepository alarmRepository;
    private final AiReportService aiReportService;
    private final EmailService emailService;
    private LocalDateTime lastIncidentTime = null;
    private static final int COOLDOWN_MINUTES = 30;

    // TODO: Threshold settings, change it to 1000
    private static final long CRITICAL_THRESHOLD = 5;

    public AlarmThresholdMonitor(AlarmRepository alarmRepository,
                                 AiReportService aiReportService, EmailService emailService) {
        this.alarmRepository = alarmRepository;
        this.aiReportService = aiReportService;
        this.emailService = emailService;
    }


    @Scheduled(fixedRate = 60000)
    public void monitorCriticalAlarms() {

        LocalDateTime lastHour = LocalDateTime.now().minusHours(1);

        long criticalCount =
                alarmRepository.countBySeverityAndRaisedTimeAfter("CRITICAL", lastHour);

        if (criticalCount >= CRITICAL_THRESHOLD) {

            if (lastIncidentTime != null &&
                    lastIncidentTime.isAfter(LocalDateTime.now().minusMinutes(COOLDOWN_MINUTES))) {

                System.out.println("⏳ Incident already reported recently. Skipping email...");
                return;
            }

            lastIncidentTime = LocalDateTime.now();

            System.out.println("🚨 INCIDENT TRIGGERED!");

            String report = aiReportService.generateIncidentReport(criticalCount);

            emailService.sendSimpleMail(
                    "swaindebasish049@gmail.com",
                    "VNMS Critical Alarm Incident Report",
                    report
            );

            System.out.println("📩 Incident email sent successfully!");
        } else {
            System.out.println(
                    "✅ Alarm check OK: " + criticalCount +
                            " critical alarms in last 1 hour"
            );
        }
    }
}
