package com.omega.stocksync.controller;

import com.omega.stocksync.dto.ProductDto;
import com.omega.stocksync.service.VendorService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendor")
public class VendorController {
  private final VendorService vendorService;

  public VendorController(@Qualifier("vendorBService") VendorService vendorService) {
    this.vendorService = vendorService;
  }

  @GetMapping("a/product")
  public List<ProductDto> getAProducts() {
    return Arrays.asList(
        new ProductDto("ABC123", "Product A", 8, "VENDOR_A"),
        new ProductDto("LMN789", "Product C", 0, "VENDOR_A"),
        new ProductDto("DEF123", "Product D", 25, "VENDOR_A"),
        new ProductDto("EFG456", "Product B", 30, "VENDOR_A"));
  }

  @GetMapping("b/product")
  public List<ProductDto> getBProducts() {
    return vendorService.fetch();
  }
}
