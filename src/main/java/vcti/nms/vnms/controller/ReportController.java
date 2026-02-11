package vcti.nms.vnms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vcti.nms.vnms.service.PdfReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final PdfReportService pdfService;

    public ReportController(PdfReportService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/critical/pdf")
    public String generateCriticalPdf(
            @RequestParam(defaultValue = "1") int hours) {

        return pdfService.generatePdf(hours);
    }
}

