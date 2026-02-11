package vcti.nms.vnms.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import vcti.nms.vnms.repository.AlarmRepository;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfReportService {

    private final AlarmRepository alarmRepository;

    public PdfReportService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    public String generatePdf(int hours) {

        try {

            LocalDateTime fromTime =
                    LocalDateTime.now().minusHours(hours);

            long count =
                    alarmRepository.countBySeverityAndRaisedTimeAfter(
                            "CRITICAL", fromTime);

            String folderPath = "reports";
            java.io.File folder = new java.io.File(folderPath);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            String fileName = folderPath + "/critical-alarms-" + hours + "h.pdf";


            Document document = new Document();
            PdfWriter.getInstance(document,
                    new FileOutputStream(fileName));

            document.open();

            // Title
            Font titleFont =
                    new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title =
                    new Paragraph("VCTI VNMS Incident Report",
                            titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "Generated at: " +
                            LocalDateTime.now()
                                    .format(
                                            DateTimeFormatter
                                                    .ofPattern(
                                                            "yyyy-MM-dd HH:mm:ss"
                                                    )
                                    )
            ));

            document.add(new Paragraph(" "));
            document.add(new Paragraph(
                    "CRITICAL alarms in last "
                            + hours + " hour(s): "
                            + count
            ));

            document.close();

            System.out.println(
                    "📄 PDF generated: " + fileName);

            return fileName;

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }
}
