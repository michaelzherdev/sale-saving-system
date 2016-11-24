package com.mzherdev.salesavingsystem.controller;

import com.mzherdev.salesavingsystem.model.Discount;
import com.mzherdev.salesavingsystem.model.Product;
import com.mzherdev.salesavingsystem.service.DiscountService;
import com.mzherdev.salesavingsystem.service.ProductService;
import com.mzherdev.salesavingsystem.tools.TimeUtils;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private DiscountService discountService;

    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        executorService.scheduleAtFixedRate(new DiscountChangeTask(), 0, 1, TimeUnit.HOURS);
        return "redirect:/products";
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String showAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();

        LocalDateTime now = LocalDateTime.now();

        Optional<Discount> optionalDiscount = discountService.getAllDiscounts()
                .stream()
                .filter(d -> TimeUtils.isBetween(now, d.getTimeStart(), d.getTimeEnd()))
                .findAny();

        Discount discount;

        if (optionalDiscount.isPresent()) {
            discount = optionalDiscount.get();
        } else {
            discount = new Discount();
        }

        model.addAttribute("discount", discount);
        model.addAttribute("products", products);
        return "products/list";
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public String editProduct(
            @ModelAttribute("productForm") @Validated Product product,
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "products/productform";
        } else {

            // Add message to flash scope
            redirectAttributes.addFlashAttribute("css", "success");
            if (product.isNew()) {
                redirectAttributes.addFlashAttribute("msg",
                        "Product Added Successfully!");
                productService.add(product);
            } else {
                redirectAttributes.addFlashAttribute("msg",
                        "Product Updated Successfully!");
                productService.edit(product);
            }
            return "redirect:/products/" + product.getId();

        }
    }

    // show add product form
    @RequestMapping(value = "/products/add", method = RequestMethod.GET)
    public String showAddProductForm(Model model) {
        Product product = new Product();
        product.setName("");
        product.setPrice(0.0);
        model.addAttribute("productForm", product);
        return "products/productform";
    }

    // show update form
    @RequestMapping(value = "/products/{id}/update", method = RequestMethod.GET)
    public String showUpdateProductForm(@PathVariable("id") int id, Model model) {
        Product product = productService.getProduct(id);
        model.addAttribute("productForm", product);
        return "products/productform";
    }

    @RequestMapping(value = "/products/{id}/delete", method = RequestMethod.POST)
    public String deleteProduct(@PathVariable("id") int id,
                                final RedirectAttributes redirectAttributes) {
        try {
            productService.delete(id);
        } catch (Exception e) {
            return "exception";
        }

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Product is deleted!");

        return "redirect:/products";
    }

    // show product details
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public String showProduct(@PathVariable("id") int id, Model model) {
        Product product = productService.getProduct(id);
        if (product == null) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "Product not found");
        }
        model.addAttribute("product", product);
        return "products/show";
    }

    @RequestMapping(value = "/discounts", method = RequestMethod.GET)
    public String showAllDiscounts(Model model) {
        model.addAttribute("discounts", discountService.getAllDiscounts());
        return "discounts/discountlist";
    }

    private class DiscountChangeTask implements Runnable {
        @Override
        public void run() {
            org.slf4j.LoggerFactory.getLogger(getClass().getSimpleName()).info("run " + LocalDateTime.now());
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

//                LocalDateTime startDate = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), now.getHour(), 0, 0);
                LocalDateTime startDate = now;
                LocalDateTime endDate = startDate.plusHours(1);

                Discount discount = new Discount(startDate, endDate, amount, product);
                discountService.add(discount);

                product.getDiscounts().add(discount);
                productService.edit(product);
            }
        }
    }
}
