package com.mzherdev.salesavingsystem.controller.task;

import com.mzherdev.salesavingsystem.model.Discount;
import com.mzherdev.salesavingsystem.model.Product;
import com.mzherdev.salesavingsystem.service.DiscountService;
import com.mzherdev.salesavingsystem.service.ProductService;
import com.mzherdev.salesavingsystem.tools.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

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
        List<Product> products = productService.getAllProducts();

        LocalDateTime now = LocalDateTime.now();
        Optional<Discount> optionalDiscount = discountService.getAllDiscounts()
                .stream()
                .filter(d -> TimeUtils.isBetween(now, d.getTimeStart(), d.getTimeEnd()))
                .findAny();

        if (!optionalDiscount.isPresent()) {
            int minDiscount = Discount.MIN_DISCOUNT_AMOUNT;
            int maxDiscount = Discount.MAX_DISCOUNT_AMOUNT;
            int amount = ThreadLocalRandom.current().nextInt(minDiscount, maxDiscount + 1);

            int randomProductIndex = ThreadLocalRandom.current().nextInt(products.size());
            Product product = products.get(randomProductIndex);

            LocalDateTime startDate = now;
            LocalDateTime endDate = startDate.plusHours(1);

            Discount discount = new Discount(startDate, endDate, amount, product);
            discountService.add(discount);

            product.getDiscounts().add(discount);
            productService.edit(product);
        }
    }
}
