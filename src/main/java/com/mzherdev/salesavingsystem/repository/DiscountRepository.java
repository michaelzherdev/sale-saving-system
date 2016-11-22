package com.mzherdev.salesavingsystem.repository;

import com.mzherdev.salesavingsystem.model.Discount;

import java.util.List;

public interface DiscountRepository {

    void add(Discount discount);

    void edit(Discount discount);

    Discount getDiscount(int discountId);

    List<Discount> getAllDiscounts();
}
