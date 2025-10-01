package com.omega.stocksync.scheduler;

import com.omega.stocksync.service.StockSyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(
    name = "stock-sync.scheduler.enabled",
    havingValue = "true",
    matchIfMissing = false)
public class StockSyncScheduler {
  private final StockSyncService stockSyncService;

  public StockSyncScheduler(StockSyncService stockSyncService) {
    this.stockSyncService = stockSyncService;
  }

  @Scheduled(cron = "${stock-sync.scheduler.cron}")
  public void syncStockLevels() {
    log.info("=== Stock Sync Scheduler Triggered ===");
    try {
      stockSyncService.syncAllVendors();
    } catch (Exception e) {
      log.error("Error during scheduled stock sync", e);
    }
  }
}
