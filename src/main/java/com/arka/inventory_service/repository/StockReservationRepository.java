package com.arka.inventory_service.repository;

import com.arka.inventory_service.enums.ReservationStatus;
import com.arka.inventory_service.model.StockReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface StockReservationRepository extends JpaRepository<StockReservation, UUID> {

    List<StockReservation> findAllByCartId(UUID cartId);
    List<StockReservation> findAllByStatusAndCartId(ReservationStatus status,  UUID cartId);
    List<StockReservation> findAllByExpiresAtBefore(LocalDateTime time);
    List<StockReservation> findAllByStatusAndExpiresAtBefore(ReservationStatus type, LocalDateTime time);
    List<StockReservation> findAllByStatus(ReservationStatus status);
    List<StockReservation> findAllByCartIdAndCreatedAtAfter(UUID cartId, LocalDateTime dateTime);

}
