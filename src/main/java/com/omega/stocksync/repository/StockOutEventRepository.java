package com.omega.stocksync.repository;

import com.omega.stocksync.entity.StockOutEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockOutEventRepository extends JpaRepository<StockOutEvent, Long> {}
