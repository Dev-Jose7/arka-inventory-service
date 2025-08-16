package com.arka.inventory_service.schedule;

import com.arka.inventory_service.service.IStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockReservationScheduler {

    private final IStockService stockService;

    /**
     * Releases expired stock reservations every 15 minutes.
     */
    @Scheduled(fixedRate = 900_000) // every 15 minutes
    public void releaseExpiredReservations() {
        log.info("[Scheduler] Running stock reservation cleanup job...");
        stockService.updateStockByExpiredReservation();
        log.info("[Scheduler] Stock reservation cleanup job completed.");
    }
}
