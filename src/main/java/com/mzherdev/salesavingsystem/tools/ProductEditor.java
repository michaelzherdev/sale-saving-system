package com.mzherdev.salesavingsystem.tools;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.mzherdev.salesavingsystem.model.Product;
import com.mzherdev.salesavingsystem.service.ProductService;

// Used to get product by id from stringValue in addOrderItemForm
public class ProductEditor extends PropertyEditorSupport {
	
	@Autowired
	private ProductService productService;

	public ProductEditor(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		int productId = Integer.parseInt(text);
		Product product = productService.findById(productId);
		setValue(product);
	}
}
