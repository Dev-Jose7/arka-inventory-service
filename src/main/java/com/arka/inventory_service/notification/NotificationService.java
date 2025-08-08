package com.arka.inventory_service.notification;

import com.arka.inventory_service.model.Product;
import com.arka.inventory_service.model.ProductVariant;
import com.arka.inventory_service.model.Stock;
import com.arka.inventory_service.notification.email.impl.NewProductLineEmailSender;
import com.arka.inventory_service.notification.email.impl.NewProductVariantEmailSender;
import com.arka.inventory_service.notification.email.impl.StockEntryEmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NewProductLineEmailSender newProductLineEmailSender;
    private final NewProductVariantEmailSender newProductVariantEmailSender;
    private final StockEntryEmailSender stockEntryEmailSender;

    @Async
    public void notifyNewProduct(   Product product){
        newProductLineEmailSender.createMail(
                "arka_inventory_service_test@gmail.com",
                product.getName(), product.getCategory().getName(),
                product.getBrand().getName());
    }

    @Async
    public void notifyNewVariant(ProductVariant productVariant){
        newProductVariantEmailSender.createMail(
                "arka_inventory_service_test@gmail.com",
                productVariant.getName(),
                productVariant.getProduct().getName(),
                productVariant.getSku(),
                productVariant.getPrice()
        );
    }

    @Async
    public void notifyStockEntry(Stock stock) {
        stockEntryEmailSender.createMail(
                "arka_inventory_service_test@gmail.com",
                stock.getProductVariant().getName(),
                stock.getProductVariant().getSku(),
                stock.getStock(),
                stock.getWarehouse().getName()
        );
    }


}
