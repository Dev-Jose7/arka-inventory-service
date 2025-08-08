package com.arka.inventory_service.notification.email.base;

import com.arka.inventory_service.notification.email.IEmailService;
import com.arka.inventory_service.exception.EmailSendException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractJavaMailService implements IEmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String senderEmail;

    @Override
    public void sendMail(String toEmail, String subject, String body){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setFrom(senderEmail);

            mailSender.send(message);
        } catch (MessagingException ex) {
            log.error("Failed to send email to {}: {}", toEmail, ex.getMessage(), ex);
            throw new EmailSendException("Failed to send email to " + toEmail);
        }
    }
}
