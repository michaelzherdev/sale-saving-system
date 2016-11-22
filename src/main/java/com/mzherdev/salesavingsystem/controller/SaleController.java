package com.mzherdev.salesavingsystem.controller;

import com.mzherdev.salesavingsystem.model.*;
import com.mzherdev.salesavingsystem.service.DiscountService;
import com.mzherdev.salesavingsystem.service.OrderItemService;
import com.mzherdev.salesavingsystem.service.ProductService;
import com.mzherdev.salesavingsystem.service.SaleService;
import com.mzherdev.salesavingsystem.tools.ProductEditor;
import com.mzherdev.salesavingsystem.tools.TimeUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class SaleController {

    Logger log = LoggerFactory.getLogger(SaleController.class.getSimpleName());

    private List<OrderItem> orderItems = new ArrayList<>();

    @Autowired
    private ProductService productService;

    @Autowired
    private SaleService saleService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private DiscountService discountService;

    @RequestMapping(value = "/sales", method = RequestMethod.GET)
    public String showAllSales(Model model) {
        model.addAttribute("sales", saleService.getAllSales());
        return "sales/saleslist";
    }

    // save sale
    @RequestMapping(value = "/sales", method = RequestMethod.POST)
    public String saveSale(@ModelAttribute("saleForm") @Validated Sale sale,
                           BindingResult result, Model model,
                           final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "Some error happened, try again.");
            return "sales/saleform";
        }

        if (orderItems.isEmpty()) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "You must add at least one item!");
            return "sales/saleform";
        }

        log.info("save sale" + sale);

        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Sale Added Successfully!");

        Optional<Discount> optionalDiscount = discountService.getAllDiscounts()
                .stream()
                .filter(d -> TimeUtils.isBetween(LocalDateTime.now(), d.getTimeStart(), d.getTimeEnd()))
                .findAny();

        double cost = orderItems.stream()
                .mapToDouble(oi -> oi.getSum())
                .sum();

        double costWithDiscount = orderItems.stream()
                .mapToDouble(oi -> countOrderItemCost(oi, optionalDiscount))
                .sum();

        sale.setItems(orderItems);
        sale.setCost(cost);
        sale.setCostWithDiscount(costWithDiscount);
        model.addAttribute("saleForm", sale);
        model.addAttribute("items", orderItems);

        sale = saleService.add(sale);

        if (sale.getItems() != null)
            for (OrderItem orderItem : sale.getItems()) {
                orderItem.setSale(sale);
                orderItemService.edit(orderItem);
            }
        orderItems.clear();

        populateOrderItemForm(model);

        return "redirect:/sales/" + sale.getId();
    }

    // show add sale form
    @RequestMapping(value = "/sales/add", method = RequestMethod.GET)
    public String showAddSaleForm(Model model) {
        Sale sale;
        if (orderItems.isEmpty()) {
            sale = new Sale();
            List<OrderItem> items = new ArrayList<>();
            sale.setItems(items);
            model.addAttribute("saleForm", sale);
            model.addAttribute("items", items);
        } else {
            Optional<Discount> optionalDiscount = discountService.getAllDiscounts()
                    .stream()
                    .filter(d -> TimeUtils.isBetween(LocalDateTime.now(), d.getTimeStart(), d.getTimeEnd()))
                    .findAny();

            double cost = orderItems.stream()
                    .mapToDouble(oi -> oi.getSum())
                    .sum();

            double costWithDiscount = orderItems.stream()
                    .mapToDouble(oi -> countOrderItemCost(oi, optionalDiscount))
                    .sum();

            sale = new Sale();
            sale.setItems(orderItems);
            sale.setCost(cost);
            sale.setCostWithDiscount(costWithDiscount);
            model.addAttribute("saleForm", sale);
            model.addAttribute("items", sale.getItems());
        }
        return "sales/saleform";
    }

    // show add order item form
    @RequestMapping(value = "/sales/addOrderItem", method = RequestMethod.GET)
    public String showAddOrderItemForm(Model model) {

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(null);
        orderItem.setQuantity(1);

        model.addAttribute("sale", new Sale());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("product", new Product());
        model.addAttribute("orderItemForm", orderItem);

        populateOrderItemForm(model);

        return "sales/orderitemform";

    }

    // show sale
    @RequestMapping(value = "/sales/{id}", method = RequestMethod.GET)
    public String showSale(@PathVariable("id") int id, Model model) {

        Sale sale = saleService.getSale(id);
        if (sale == null) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "Sale not found");
        }
        model.addAttribute("sale", sale);

        return "sales/showsale";
    }


    // save order item
    @RequestMapping(value = "/sales/addOrderItem", method = RequestMethod.POST)
    public String saveOrderItem(
            @ModelAttribute("orderItemForm") @Validated OrderItem orderItem,
            BindingResult result, Model model,
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            populateOrderItemForm(model);
            return "sales/orderitemform";
        } else {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "Order Item Added Successfully!");
            double sum = orderItem.getProduct().getPrice() * orderItem.getQuantity();
            orderItem.setSum(sum);
            orderItemService.add(orderItem);
            orderItems.add(orderItem);
            return "redirect:/sales/add";
        }
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Product.class, new ProductEditor(productService));
    }

    @RequestMapping(value = "/sales/add")
    @ModelAttribute("items")
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    private void populateOrderItemForm(Model model) {
        List<Integer> numbersList = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++)
            numbersList.add(i);
        model.addAttribute("numberList", numbersList);

        Map<Integer, String> productMap = productService.getAllProducts()
                .stream()
                .collect(Collectors.toMap(Product::getId, Product::getName));
        model.addAttribute("productMap", productMap);
    }

    private double countOrderItemCost(OrderItem oi, Optional<Discount> optionalDiscount) {
        double orderItemCost = oi.getSum();
        if(optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();
            if (oi.getProduct().getId() == discount.getProduct().getId())
                orderItemCost = orderItemCost * (1 - discount.getAmount() / 100.0);
        }
        return orderItemCost;
    }
}