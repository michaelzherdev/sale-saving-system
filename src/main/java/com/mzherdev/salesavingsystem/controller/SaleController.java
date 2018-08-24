package com.mzherdev.salesavingsystem.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mzherdev.salesavingsystem.model.Discount;
import com.mzherdev.salesavingsystem.model.OrderItem;
import com.mzherdev.salesavingsystem.model.Product;
import com.mzherdev.salesavingsystem.model.Sale;
import com.mzherdev.salesavingsystem.service.DiscountService;
import com.mzherdev.salesavingsystem.service.OrderItemService;
import com.mzherdev.salesavingsystem.service.ProductService;
import com.mzherdev.salesavingsystem.service.SaleService;
import com.mzherdev.salesavingsystem.tools.ProductEditor;

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

    @GetMapping(value = "/sales")
    public String showAllSales(Model model) {
        model.addAttribute("sales", saleService.getAllSales());
        return "sales/saleslist";
    }

    // save sale
    @PostMapping(value = "/sales")
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

        Discount discount = discountService.findActiveDiscount(LocalDateTime.now());

        BigDecimal cost = orderItems.stream()
                .map(oi -> oi.getSum())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal costWithDiscount = orderItems.stream()
                .map(oi -> countOrderItemCost(oi, discount))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sale.setItems(orderItems);
        sale.setCost(cost);
        sale.setCostWithDiscount(costWithDiscount);
        model.addAttribute("saleForm", sale);
        model.addAttribute("items", orderItems);

        sale = saleService.save(sale);

        if (sale.getItems() != null)
            for (OrderItem orderItem : sale.getItems()) {
                orderItem.setSale(sale);
                orderItemService.save(orderItem);
            }
        orderItems.clear();

        populateOrderItemForm(model);

        return "redirect:/sales/" + sale.getId();
    }

    // show add sale form
    @GetMapping(value = "/sales/add")
    public String showAddSaleForm(Model model) {
        Sale sale;
        if (orderItems.isEmpty()) {
            sale = new Sale();
            List<OrderItem> items = new ArrayList<>();
            sale.setItems(items);
            model.addAttribute("saleForm", sale);
            model.addAttribute("items", items);
        } else {
            Discount discount = discountService.findActiveDiscount(LocalDateTime.now());

            BigDecimal cost = orderItems.stream()
                    .map(oi -> oi.getSum())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal costWithDiscount = orderItems.stream()
                    .map(oi -> countOrderItemCost(oi, discount))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

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
    @GetMapping(value = "/sales/addOrderItem")
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
    @GetMapping(value = "/sales/{id}")
    public String showSale(@PathVariable("id") int id, Model model) {

        Sale sale = saleService.findById(id);
        if (sale == null) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "Sale not found");
        }
        model.addAttribute("sale", sale);

        return "sales/showsale";
    }


    // save order item
    @PostMapping(value = "/sales/addOrderItem")
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
            BigDecimal sum = orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            orderItem.setSum(sum);
            orderItemService.save(orderItem);
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

    private BigDecimal countOrderItemCost(OrderItem oi, Discount discount) {
        BigDecimal orderItemCost = oi.getSum();
        if(discount != null) {
            if (oi.getProduct().getId() == discount.getProduct().getId())
                orderItemCost = orderItemCost.multiply(BigDecimal.valueOf(1 - discount.getAmount() / 100.0));
        }
        return orderItemCost;
    }
}