package com.mzherdev.salesavingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mzherdev.salesavingsystem.service.DiscountService;

@Controller
public class DiscountController {

	private static final int PAGE_SIZE = 5;

	@Autowired
	DiscountService discountService;

	@GetMapping(value = "/discounts")
	public String showDiscounts(Model model, @RequestParam(defaultValue = "1") int page) {
		model.addAttribute("discounts", discountService.findAll(PageRequest.of(page - 1, PAGE_SIZE, Sort.Direction.DESC, "id")));
		return "/discounts";
	}
}
