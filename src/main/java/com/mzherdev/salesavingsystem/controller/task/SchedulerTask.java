package com.mzherdev.salesavingsystem.controller.task;

import static com.mzherdev.salesavingsystem.model.Discount.MAX_DISCOUNT_AMOUNT;
import static com.mzherdev.salesavingsystem.model.Discount.MIN_DISCOUNT_AMOUNT;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mzherdev.salesavingsystem.model.Discount;
import com.mzherdev.salesavingsystem.model.Product;
import com.mzherdev.salesavingsystem.service.DiscountService;
import com.mzherdev.salesavingsystem.service.ProductService;

@Component
public class SchedulerTask {

    private static final Logger log = LoggerFactory.getLogger(SchedulerTask.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private DiscountService discountService;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void changeProductDiscount() {
        log.info("SchedulerTask running...");

        LocalDateTime now = LocalDateTime.now();
        Discount currentDiscount = discountService.findActiveDiscount(now);

        if (currentDiscount == null) {
            int amount = ThreadLocalRandom.current().nextInt(MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT + 1);
            Product product = productService.findRandom();
            LocalDateTime endDate = now.plusHours(1).minusMinutes(1);
            Discount discount = new Discount(now, endDate, amount, product);
            discountService.save(discount);
        }
        log.info("SchedulerTask finished");
    }
}
