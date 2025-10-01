package com.omega.stocksync.dto;

import lombok.Builder;

@Builder
public record ProductDto(String sku, String name, Integer stockQuantity, String vendorName) {}
