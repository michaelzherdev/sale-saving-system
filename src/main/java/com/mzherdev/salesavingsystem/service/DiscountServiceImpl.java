package com.mzherdev.salesavingsystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mzherdev.salesavingsystem.model.Discount;
import com.mzherdev.salesavingsystem.repository.DiscountRepository;

@Service("dscountService")
@Transactional
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    ProductService productService;

    @Override
    public void save(Discount discount) {
        discountRepository.save(discount);
    }

    @Override
    public void saveForRandomProduct(Discount discount) {
        discount.setProduct(productService.findRandom());
        discountRepository.save(discount);
    }

    @Override
    public Page<Discount> findAll(Pageable pageable) {
        return discountRepository.findAll(pageable);
    }

    @Override
    public Discount findActiveDiscount(LocalDateTime dateTime) {
        return discountRepository.findActiveDiscount(dateTime);
    }
}
