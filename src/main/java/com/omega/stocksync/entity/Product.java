package com.omega.stocksync.entity;

import com.omega.stocksync.common.AuditInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "product",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"sku", "vendor"})})
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String sku;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer stockQuantity;

  @Column(nullable = false)
  private String vendor;

  @Embedded private AuditInfo auditInfo;
}
