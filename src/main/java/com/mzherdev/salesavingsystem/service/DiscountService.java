package com.mzherdev.salesavingsystem.service;

import java.time.LocalDateTime;
import java.util.List;

import com.mzherdev.salesavingsystem.model.Discount;

public interface DiscountService {

    void save(Discount discount);

    List<Discount> findAllOrdered();
    Discount findActiveDiscount(LocalDateTime dateTime);
}
