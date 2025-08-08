package com.arka.inventory_service.notification.email.impl;

import com.arka.inventory_service.notification.email.base.AbstractJavaMailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NewProductLineEmailSender extends AbstractJavaMailService {

    public NewProductLineEmailSender(JavaMailSender mailSender) {
        super(mailSender);
    }

    public void createMail(String toEmail, String productName, String category, String brand) {
        String subject = "🆕 Nueva Línea de Producto Registrada: " + productName;

        String body = """
            <html>
            <body style='font-family: Arial, sans-serif;'>
                <h2 style='color: #1976d2;'>🆕 Nueva Línea de Producto</h2>
                <p>Se ha registrado una nueva línea de producto en el sistema de inventario:</p>
                <ul>
                    <li><strong>Nombre:</strong> %s</li>
                    <li><strong>Categoría:</strong> %s</li>
                    <li><strong>Marca:</strong> %s</li>
                </ul>
                <p>Ya puedes comenzar a registrar variantes y controlar su inventario.</p>
                <br>
                <p style='font-size: 12px; color: #999;'>Este mensaje fue generado automáticamente por el sistema de inventario de Arka.</p>
            </body>
            </html>
            """.formatted(productName, category, brand);

        sendMail(toEmail, subject, body);
    }
}