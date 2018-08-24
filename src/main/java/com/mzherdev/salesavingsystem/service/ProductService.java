package com.mzherdev.salesavingsystem.service;

import java.util.List;

import com.mzherdev.salesavingsystem.model.Product;

public interface ProductService {

	void save(Product product);

	void delete(int productId);

	Product findById(int productId);

	List<Product> getAllProducts();

	Product findRandom();

	void evictCache();
}
