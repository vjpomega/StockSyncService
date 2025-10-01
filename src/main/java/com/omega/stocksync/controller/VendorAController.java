package com.omega.stocksync.controller;

import com.omega.stocksync.dto.ProductDto;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vendor-a")
public class VendorAController {
  @GetMapping("/product")
  public List<ProductDto> getProducts() {
    return Arrays.asList(
        new ProductDto("ABC123", "Product A", 8),
        new ProductDto("LMN789", "Product C", 0),
        new ProductDto("DEF123", "Product D", 25),
        new ProductDto("EFG456", "Product B", 30));
  }
}
