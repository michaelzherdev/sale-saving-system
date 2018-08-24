package com.mzherdev.salesavingsystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mzherdev.salesavingsystem.model.Discount;
import com.mzherdev.salesavingsystem.repository.DiscountRepository;

@Service("dscountService")
@Transactional
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    DiscountRepository discountRepository;

    @Override
    public void save(Discount discount) {
        discountRepository.save(discount);
    }

    @Override
    public List<Discount> findAllOrdered() {
        return discountRepository.findOrdered();
    }

    @Override
    public Discount findActiveDiscount(LocalDateTime dateTime) {
        return discountRepository.findActiveDiscount(dateTime);
    }
}
