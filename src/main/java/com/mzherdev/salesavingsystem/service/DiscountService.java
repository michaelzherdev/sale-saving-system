package com.mzherdev.salesavingsystem.service;

import java.time.LocalDateTime;
import java.util.List;

import com.mzherdev.salesavingsystem.model.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiscountService {

    void save(Discount discount);

    void saveForRandomProduct(Discount discount);

    Page<Discount> findAll(Pageable pageable);

    Discount findActiveDiscount(LocalDateTime dateTime);
}
