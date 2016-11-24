package com.mzherdev.salesavingsystem.service;

import com.mzherdev.salesavingsystem.model.Product;

import java.util.List;

public interface ProductService {

	void add(Product product);

	void edit(Product product);

	void delete(int productId);

	Product getProduct(int productId);

	List<Product> getAllProducts();

	void evictCache();
}
