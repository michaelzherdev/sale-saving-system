package com.mzherdev.salesavingsystem.controller;

import static com.mzherdev.salesavingsystem.tools.AppExceptionHandler.convertToString;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.mzherdev.salesavingsystem.model.Discount;
import com.mzherdev.salesavingsystem.model.Product;
import com.mzherdev.salesavingsystem.service.DiscountService;
import com.mzherdev.salesavingsystem.service.ProductService;

@Controller
public class ProductController extends BasePageController {

	private final Logger log = LoggerFactory.getLogger(ProductController.class.getSimpleName());

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
							  BindingResult result) {
		if (result.hasErrors()) {
			log.error(convertToString(result.getAllErrors()));
			return "redirect:/products";
		} else {
			productService.save(product);
			return "redirect:/products";
		}
	}

	@GetMapping(value = "/products/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		productService.delete(id);
		return "redirect:/products";
	}
}
