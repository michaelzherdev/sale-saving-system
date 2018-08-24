package com.mzherdev.salesavingsystem.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mzherdev.salesavingsystem.model.Discount;
import com.mzherdev.salesavingsystem.model.Product;
import com.mzherdev.salesavingsystem.service.DiscountService;
import com.mzherdev.salesavingsystem.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private DiscountService discountService;

	@GetMapping(value = "/")
	public String index() {
		return "redirect:/products";
	}

	@GetMapping(value = "/products")
	public String showAllProducts(Model model) {
		List<Product> products = productService.getAllProducts();
		Discount discount = discountService.findActiveDiscount(LocalDateTime.now());
		model.addAttribute("discount", discount != null ? discount : new Discount());
		model.addAttribute("products", products);
		return "products/list";
	}

	@PostMapping(value = "/products")
	public String editProduct(
			@ModelAttribute("productForm") @Validated Product product,
			BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "products/productform";
		} else {

			// Add message to flash scope
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg",
					product.isNew() ? "Product Added Successfully!" : "Product Updated Successfully!");
			productService.save(product);
			return "redirect:/products/" + product.getId();

		}
	}

	// show add product form
	@GetMapping(value = "/products/add")
	public String showAddProductForm(Model model) {
		Product product = new Product();
		product.setName("");
		product.setPrice(BigDecimal.ZERO);
		model.addAttribute("productForm", product);
		return "products/productform";
	}

	// show update form
	@GetMapping(value = "/products/{id}/update")
	public String showUpdateProductForm(@PathVariable("id") int id, Model model) {
		Product product = productService.findById(id);
		model.addAttribute("productForm", product);
		return "products/productform";
	}

	@PostMapping(value = "/products/{id}/delete")
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
	@GetMapping(value = "/products/{id}")
	public String showProduct(@PathVariable("id") int id, Model model) {
		Product product = productService.findById(id);
		if (product == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Product not found");
		}
		model.addAttribute("product", product);
		return "products/show";
	}

	@GetMapping(value = "/discounts")
	public String showAllDiscounts(Model model) {
		model.addAttribute("discounts", discountService.findAllOrdered());
		return "discounts/discountlist";
	}
}
