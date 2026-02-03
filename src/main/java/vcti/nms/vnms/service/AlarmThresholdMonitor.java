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
    private final ReportFormatter reportFormatter;
    private final AiReportService ollamaService;


    // TODO: Threshold settings, change it to 1000
    private static final long CRITICAL_THRESHOLD = 5;

    public AlarmThresholdMonitor(AlarmRepository alarmRepository,
                                 AiReportService aiReportService, EmailService emailService, ReportFormatter reportFormatter, AiReportService ollamaService) {
        this.alarmRepository = alarmRepository;
        this.aiReportService = aiReportService;
        this.emailService = emailService;
        this.reportFormatter = reportFormatter;
        this.ollamaService = ollamaService;
    }


    @Scheduled(fixedRate = 60000)
    public void monitorCriticalAlarms() {

        LocalDateTime lastHour = LocalDateTime.now().minusHours(1);

        long criticalCount =
                alarmRepository.countBySeverityAndRaisedTimeAfter("CRITICAL", lastHour);

        if (criticalCount >= CRITICAL_THRESHOLD) {

            String aiReport = ollamaService.generateIncidentReport(criticalCount);

            String htmlReport =
                    reportFormatter.formatIncidentReportHtml(aiReport, criticalCount);

            emailService.sendHtmlMail(
                    "swaindebasish049@gmail.com",
                    "🚨 VNMS Alarm Storm Incident Report",
                    htmlReport
            );
        }
        else {
            System.out.println(
                    "✅ Alarm check OK: " + criticalCount +
                            " critical alarms in last 1 hour"
            );
        }
    }
}
