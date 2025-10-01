package com.omega.stocksync.controller.impl;

import com.omega.stocksync.controller.ProductController;
import com.omega.stocksync.dto.ProductDto;
import com.omega.stocksync.service.ProductService;
import com.omega.stocksync.service.StockSyncService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductControllerImpl implements ProductController {

  private final StockSyncService stockSyncService;
  private final ProductService productService;

  public ProductControllerImpl(StockSyncService stockSyncService, ProductService productService) {
    this.stockSyncService = stockSyncService;
    this.productService = productService;
  }

  @PostMapping("/sync")
  public ResponseEntity<String> triggerSync() {
    stockSyncService.syncAllVendors();
    return ResponseEntity.ok("Stock synchronization triggered successfully");
  }

  @GetMapping
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    return ResponseEntity.ok(productService.getAllProducts());
  }
}
