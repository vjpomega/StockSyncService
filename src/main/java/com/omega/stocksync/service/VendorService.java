package com.omega.stocksync.service;

import com.omega.stocksync.dto.ProductDto;
import java.util.List;

public interface VendorService {
  List<ProductDto> fetch();

  String getVendorName();
}
