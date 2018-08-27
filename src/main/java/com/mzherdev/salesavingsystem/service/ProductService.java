package com.mzherdev.salesavingsystem.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mzherdev.salesavingsystem.model.Product;

public interface ProductService {

	void save(Product product);

	void delete(int productId);

	Product findById(int productId);

	List<Product> findAll();

	Page<Product> findAll(Pageable pageable);

	Product findRandom();

	void evictCache();
}
