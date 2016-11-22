package com.mzherdev.salesavingsystem.tools;

import com.mzherdev.salesavingsystem.model.Product;
import com.mzherdev.salesavingsystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyEditorSupport;

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
		Product product = productService.getProduct(productId);
		setValue(product);
	}
}
