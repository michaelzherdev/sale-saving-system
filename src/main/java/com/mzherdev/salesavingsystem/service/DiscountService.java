package com.mzherdev.salesavingsystem.service;

import com.mzherdev.salesavingsystem.model.Discount;

import java.util.List;

public interface DiscountService {

    void add(Discount discount);

    void edit(Discount discount);

    Discount getDiscount(int discountId);

    List<Discount> getAllDiscounts();
}
