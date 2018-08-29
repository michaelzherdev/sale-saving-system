package com.mzherdev.salesavingsystem.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mzherdev.salesavingsystem.model.Discount;
import com.mzherdev.salesavingsystem.model.OrderItem;
import com.mzherdev.salesavingsystem.model.Product;
import com.mzherdev.salesavingsystem.model.Sale;
import com.mzherdev.salesavingsystem.service.DiscountService;
import com.mzherdev.salesavingsystem.service.ProductService;
import com.mzherdev.salesavingsystem.service.SaleService;

@Controller
public class SaleController {

	private static final int PAGE_SIZE = 5;

	private final Logger log = LoggerFactory.getLogger(SaleController.class.getSimpleName());

	private List<OrderItem> orderItems = new ArrayList<>();
	private BigDecimal cost = BigDecimal.ZERO;
	private BigDecimal costWithDiscount = BigDecimal.ZERO;

	@Autowired
	private ProductService productService;

	@Autowired
	private SaleService saleService;

	@Autowired
	private DiscountService discountService;

	@GetMapping(value = "/sales")
	public String showSales(Model model, @RequestParam(defaultValue = "1") int page) {
		Page<Sale> sales = saleService.findAll(PageRequest.of(page - 1, PAGE_SIZE, Sort.Direction.DESC, "id"));
		model.addAttribute("sales", sales);
		model.addAttribute("currentPage", page);
		return "sales";
	}


	@GetMapping(value = "/saleform")
	public String showSaleForm(Model model) {
		OrderItemForm itemForm = new OrderItemForm();
		model.addAttribute("itemForm", itemForm);
		model.addAttribute("cost", cost);
		model.addAttribute("costWithDiscount", costWithDiscount);
		model.addAttribute("items", orderItems);
		model.addAttribute("products", productService.findAll());
		return "saleform";
	}

	private void clearSaleForm() {
		cost = BigDecimal.ZERO;
		costWithDiscount = BigDecimal.ZERO;
		orderItems.clear();
	}

	@PostMapping(value = "/items")
	public String addOrderItem(@ModelAttribute("itemForm") @Validated OrderItem orderItem,
							   BindingResult result) {
		if (result.hasErrors()) {
			log.error(convertToString(result.getAllErrors()));
			return "redirect:saleform";
		} else {
			orderItem.calculateSum();
			orderItems.add(orderItem);
			cost = cost.add(orderItem.getSum());
			Discount activeDiscount = discountService.findActiveDiscount(LocalDateTime.now());
			if (orderItem.isDiscountAvailable(activeDiscount)) {
				costWithDiscount = costWithDiscount.add(
						orderItem.getSum().multiply(activeDiscount.getAmountInPercent()));
			} else {
				costWithDiscount = costWithDiscount.add(orderItem.getSum());
			}
			return "redirect:saleform";
		}
	}


	@PostMapping(value = "/sales")
	public String saveSale(@ModelAttribute("saleForm") @Validated Sale sale,
						   BindingResult result) {
		if (result.hasErrors() || orderItems.isEmpty()) {
			log.error(convertToString(result.getAllErrors()));
			return "saleform";
		}

		sale.setItems(orderItems);
		sale.setCost(cost);
		sale.setCostWithDiscount(costWithDiscount);

		saleService.save(sale);
		clearSaleForm();

		return "redirect:/sales";
	}

	private String convertToString(List<ObjectError> list) {
		return list.stream().map(ObjectError::toString).reduce("", String::concat);
	}

	@ResponseBody
	@GetMapping(value = "/sales/{id}")
	public Sale findOne(@PathVariable("id") int id) {
		return saleService.findById(id);
	}

	public static class OrderItemForm {
		private Product product;
		private Integer quantity;

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
	}
}