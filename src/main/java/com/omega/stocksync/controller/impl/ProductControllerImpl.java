package com.omega.stocksync.controller.impl;

import com.omega.stocksync.controller.ProductController;
import com.omega.stocksync.service.StockSyncService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductControllerImpl implements ProductController {

  private final StockSyncService stockSyncService;

  public ProductControllerImpl(StockSyncService stockSyncService) {
    this.stockSyncService = stockSyncService;
  }

  @PostMapping("/sync")
  @Operation(
      summary = "Trigger manual sync",
      description = "Manually trigger stock synchronization from all vendors")
  public ResponseEntity<String> triggerSync() {
    stockSyncService.syncAllVendors();
    return ResponseEntity.ok("Stock synchronization triggered successfully");
  }
}
