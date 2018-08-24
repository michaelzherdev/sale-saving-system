package com.mzherdev.salesavingsystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mzherdev.salesavingsystem.model.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer>{

    @Query("SELECT d FROM Discount d ORDER BY d.timeEnd DESC")
    List<Discount> findOrdered();
    @Query(value = "SELECT * FROM discounts d WHERE ?1 BETWEEN d.time_start AND d.time_end ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Discount findActiveDiscount(LocalDateTime dateTime);
}
