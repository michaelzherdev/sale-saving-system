package com.mzherdev.salesavingsystem.service;

import com.mzherdev.salesavingsystem.model.Discount;
import com.mzherdev.salesavingsystem.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    DiscountRepository discountRepository;

    @Override
    public void add(Discount discount) {
        discountRepository.add(discount);
    }

    @Override
    public void edit(Discount discount) {
discountRepository.edit(discount);
    }

    @Override
    public Discount getDiscount(int discountId) {
        return discountRepository.getDiscount(discountId);
    }

    @Override
    public List<Discount> getAllDiscounts() {
        return discountRepository.getAllDiscounts();
    }
}
