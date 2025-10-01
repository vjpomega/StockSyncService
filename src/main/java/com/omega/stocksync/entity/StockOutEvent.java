package com.omega.stocksync.entity;

import com.omega.stocksync.common.AuditInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockOutEvent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String sku;

  @Column(nullable = false)
  private String productName;

  @Column(nullable = false)
  private String vendor;

  @Column(nullable = false)
  private Integer previousStockQty;

  @Column(nullable = false)
  private Integer newStockQuantity;

  @Embedded private AuditInfo auditInfo;
}
