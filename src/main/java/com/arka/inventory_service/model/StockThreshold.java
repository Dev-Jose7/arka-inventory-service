package com.arka.inventory_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "stock_threshold")
public class StockThreshold {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Stock stock;

    @Column(name = "min_quantity")
    private Integer minimumQuantity;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
