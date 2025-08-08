package com.arka.inventory_service.notification.email.impl;

import com.arka.inventory_service.notification.email.base.AbstractJavaMailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class StockEntryEmailSender extends AbstractJavaMailService {

    public StockEntryEmailSender(JavaMailSender mailSender) {
        super(mailSender);
    }

    public void createMail(String toEmail, String variantName, String sku, int quantity, String warehouseName) {
        String subject = "📦 Nuevo Stock Registrado: " + variantName;

        String body = """
            <html>
            <body style='font-family: Arial, sans-serif;'>
                <h2 style='color: #f57c00;'>📦 Stock Registrado</h2>
                <p>Se ha ingresado nuevo stock al sistema:</p>
                <ul>
                    <li><strong>Producto:</strong> %s</li>
                    <li><strong>SKU:</strong> %s</li>
                    <li><strong>Cantidad ingresada:</strong> %d unidades</li>
                    <li><strong>Bodega:</strong> %s</li>
                </ul>
                <p>Ya puedes visualizar el stock actualizado por variante y bodega.</p>
                <br>
                <p style='font-size: 12px; color: #999;'>Este mensaje fue generado automáticamente por el sistema de inventario de Arka.</p>
            </body>
            </html>
            """.formatted(variantName, sku, quantity, warehouseName);

        sendMail(toEmail, subject, body);
    }
}