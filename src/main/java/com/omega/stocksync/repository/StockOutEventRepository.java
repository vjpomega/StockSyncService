package com.omega.stocksync.repository;

import com.omega.stocksync.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockOutEventRepository extends JpaRepository<Product, Long> {}
