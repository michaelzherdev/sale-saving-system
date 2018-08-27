package com.mzherdev.salesavingsystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mzherdev.salesavingsystem.model.Discount;
import com.mzherdev.salesavingsystem.model.Product;
import com.mzherdev.salesavingsystem.service.DiscountService;
import com.mzherdev.salesavingsystem.service.ProductService;

@Controller
public class ProductController {

	private static final int PAGE_SIZE = 5;

	@Autowired
	private ProductService productService;

	@Autowired
	private DiscountService discountService;

	@GetMapping(value = "/")
	public String index() {
		return "redirect:/products";
	}

	@GetMapping(value = "/products")
	public String showProducts(Model model, @RequestParam(defaultValue = "1") int page) {
		Page<Product> products = productService.findAll(PageRequest.of(page - 1, PAGE_SIZE, Sort.Direction.ASC, "id"));
		Discount discount = discountService.findActiveDiscount(LocalDateTime.now());
		model.addAttribute("discount", discount != null ? discount : new Discount());
		model.addAttribute("products", products);
		model.addAttribute("currentPage", page);
		return "products";
	}

	@ResponseBody
	@GetMapping(value = "/products/{id}")
	public Product findOne(@PathVariable("id") int id) {
		return productService.findById(id);
	}

	@PostMapping(value = "/products")
	public String saveProduct(@Validated Product product,
			BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			//todo show error
//			redirectAttributes.addFlashAttribute("msg",
//					product.isNew() ? "Product Added Successfully!" : "Product Updated Successfully!");
			return "redirect:/products";
		} else {
			productService.save(product);
			return "redirect:/products";

		}
	}

	@GetMapping(value = "/products/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		try {
			productService.delete(id);
		} catch (Exception e) {
			return "exception";
		}
		return "redirect:/products";
	}

	@GetMapping(value = "/discounts")
	public String showDiscounts(Model model, @RequestParam(defaultValue = "1") int page) {
		model.addAttribute("discounts", discountService.findAll(PageRequest.of(page - 1, PAGE_SIZE, Sort.Direction.DESC, "id")));
		return "/discounts";
	}
}
