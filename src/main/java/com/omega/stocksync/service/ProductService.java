package com.omega.stocksync.service;

import com.omega.stocksync.dto.ProductDto;

import java.util.List;

public interface ProductService {

  List<ProductDto> getAllProducts();
}
