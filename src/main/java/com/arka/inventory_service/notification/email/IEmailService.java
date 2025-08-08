package com.arka.inventory_service.notification.email;

public interface IEmailService {
    void sendMail(String toEmail, String subject, String body);
}
