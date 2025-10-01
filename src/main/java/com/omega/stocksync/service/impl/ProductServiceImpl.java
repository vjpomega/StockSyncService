package com.omega.stocksync.service.impl;

import com.omega.stocksync.dto.ProductDto;
import com.omega.stocksync.entity.Product;
import com.omega.stocksync.repository.ProductRepository;
import com.omega.stocksync.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public List<ProductDto> getAllProducts() {
    return productRepository.findAll().stream().map(this::mapToResponseDTO).toList();
  }

  private ProductDto mapToResponseDTO(Product product) {
    return ProductDto.builder()
        .sku(product.getSku())
        .name(product.getName())
        .stockQuantity(product.getStockQuantity())
        .vendorName(product.getVendor())
        .build();
  }
}
