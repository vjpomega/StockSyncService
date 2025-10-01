package com.omega.stocksync.service.impl;

import com.omega.stocksync.dto.ProductDto;
import com.omega.stocksync.service.VendorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class VendorAServiceImpl implements VendorService {

  private final WebClient webClient;

  @Value("${vendor.a.url}")
  private String url;

  public VendorAServiceImpl(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.build();
  }

  @Override
  public List<ProductDto> fetch() {
    return webClient.get().uri(url).retrieve().bodyToFlux(ProductDto.class).collectList().block();
  }

  @Override
  public String getVendorName() {
    return "VENDOR_A";
  }
}
