package com.arka.inventory_service.notification.email.impl;

import com.arka.inventory_service.notification.email.base.AbstractJavaMailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NewProductVariantEmailSender extends AbstractJavaMailService {

    public NewProductVariantEmailSender(JavaMailSender mailSender) {
        super(mailSender);
    }

    public void createMail(String toEmail, String variantName, String productName, String sku, BigDecimal price) {
        String subject = "🆕 Nueva Variante de Producto: " + variantName;

        String body = """
            <html>
            <body style='font-family: Arial, sans-serif;'>
                <h2 style='color: #388e3c;'>🆕 Nueva Variante de Producto</h2>
                <p>Se ha agregado una nueva variante al catálogo de productos:</p>
                <ul>
                    <li><strong>Nombre de variante:</strong> %s</li>
                    <li><strong>Producto base:</strong> %s</li>
                    <li><strong>SKU:</strong> %s</li>
                    <li><strong>Precio:</strong> $%.2f</li>
                </ul>
                <p>Ya puedes gestionar su inventario por bodega y monitorear su disponibilidad.</p>
                <br>
                <p style='font-size: 12px; color: #999;'>Este mensaje fue generado automáticamente por el sistema de inventario de Arka.</p>
            </body>
            </html>
            """.formatted(variantName, productName, sku, price);

        sendMail(toEmail, subject, body);
    }
}