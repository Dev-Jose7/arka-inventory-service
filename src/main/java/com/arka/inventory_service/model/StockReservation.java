package com.arka.inventory_service.model;

import com.arka.inventory_service.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "stock_reservation")
public class StockReservation {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private StockMovement stockMovement;

    @Column(name = "cart_id", nullable = false)
    private UUID cartId;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}