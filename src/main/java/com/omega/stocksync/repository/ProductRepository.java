package com.omega.stocksync.repository;

import com.omega.stocksync.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findBySkuAndVendor(String sku, String vendor);
}
