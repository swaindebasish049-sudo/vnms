package vcti.nms.vnms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vcti.nms.vnms.model.EmailRequest;
import vcti.nms.vnms.service.EmailService;

@RestController
@RequestMapping("/api/notifications")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email")
    public String sendEmail(@RequestBody EmailRequest request) {

        emailService.sendSimpleMail(
                request.getTo(),
                request.getSubject(),
                request.getBody()
        );

        return "Email sent successfully";
    }
}

