package com.arka.inventory_service.notification.email.impl;

import com.arka.inventory_service.notification.email.base.AbstractJavaMailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class StockAlertEmailSender extends AbstractJavaMailService {

    public StockAlertEmailSender(JavaMailSender mailSender) {
        super(mailSender);
    }

    public void createMail(String toEmail, String productName, int currentStock, int minStock) {
        String subject = "🔔 Alerta de Stock Bajo: " + productName;

        String body = "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<h2 style='color: #d32f2f;'>⚠️ Alerta de Inventario</h2>" +
                "<p>El siguiente producto ha alcanzado su umbral mínimo de inventario:</p>" +
                "<ul>" +
                "<li><strong>Producto:</strong> " + productName + "</li>" +
                "<li><strong>Stock actual:</strong> " + currentStock + "</li>" +
                "<li><strong>Stock mínimo requerido:</strong> " + minStock + "</li>" +
                "</ul>" +
                "<p>Por favor, considera reabastecer este producto lo antes posible.</p>" +
                "<br>" +
                "<p style='font-size: 12px; color: #999;'>Este mensaje fue generado automáticamente por el sistema de inventario de Arka.</p>" +
                "</body>" +
                "</html>";

        sendMail(toEmail, subject, body);
    }
}