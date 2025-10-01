package com.omega.stocksync.service.impl;

import com.omega.stocksync.dto.ProductDto;
import com.omega.stocksync.entity.Product;
import com.omega.stocksync.entity.StockOutEvent;
import com.omega.stocksync.repository.ProductRepository;
import com.omega.stocksync.repository.StockOutEventRepository;
import com.omega.stocksync.service.StockSyncService;
import com.omega.stocksync.service.VendorService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StockSyncServiceImpl implements StockSyncService {

  private final List<VendorService> vendors;
  private final ProductRepository productRepo;
  private final StockOutEventRepository stockOutEventRepo;

  public StockSyncServiceImpl(
      List<VendorService> vendors,
      ProductRepository productRepo,
      StockOutEventRepository stockOutEventRepo) {
    this.vendors = vendors;
    this.productRepo = productRepo;
    this.stockOutEventRepo = stockOutEventRepo;
  }

  @Override
  @Transactional
  public void syncAllVendors() {
    List<CompletableFuture<List<ProductDto>>> futures =
        vendors.stream().map(v -> CompletableFuture.supplyAsync(() -> fetchWithVendor(v))).toList();

    // Merge results from all Vendors
    List<ProductDto> allProducts =
        futures.stream().map(CompletableFuture::join).flatMap(List::stream).toList();

    for (ProductDto dto : allProducts) {
      String vendorName = dto.vendorName();
      productRepo
          .findBySkuAndVendor(dto.sku(), vendorName)
          .ifPresentOrElse(
              existing -> {
                Integer prevStockQty = existing.getStockQuantity();
                if (prevStockQty > 0 && dto.stockQuantity() == 0) {
                  stockOutEventRepo.save(
                      StockOutEvent.builder()
                          .sku(dto.sku())
                          .productName(dto.name())
                          .vendor(dto.vendorName())
                          .previousStockQty(prevStockQty)
                          .newStockQuantity(0)
                          .build());
                  log.warn("OUT OF STOCK: {} - {}", dto.sku(), dto.vendorName());
                }
                existing.setStockQuantity(dto.stockQuantity());
                productRepo.save(existing);
              },
              () -> {
                Product p =
                    Product.builder()
                        .sku(dto.sku())
                        .name(dto.name())
                        .vendor(dto.vendorName())
                        .stockQuantity(dto.stockQuantity())
                        .build();
                productRepo.save(p);
              });
    }
  }

  private List<ProductDto> fetchWithVendor(VendorService vendorService) {
    String vendorName = vendorService.getVendorName();
    try {
      List<ProductDto> list = vendorService.fetch();
      log.info("{} fetched {} products", vendorName, list.size());
      return list.stream()
          .map(dto -> new ProductDto(dto.sku(), dto.name(), dto.stockQuantity(), vendorName))
          .toList();
    } catch (Exception e) {
      log.warn("{} failed", vendorName, e);
      return List.of();
    }
  }
}
