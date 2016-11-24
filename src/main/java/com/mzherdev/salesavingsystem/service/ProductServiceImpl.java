package com.mzherdev.salesavingsystem.service;

import com.mzherdev.salesavingsystem.repository.ProductRepository;
import com.mzherdev.salesavingsystem.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	@CacheEvict(value = "products", allEntries = true)
	public void add(Product product) {
		productRepository.add(product);
	}

	@Override
	@CacheEvict(value = "products", allEntries = true)
	public void edit(Product product) {
		productRepository.edit(product);
	}

	@Override
	@CacheEvict(value = "products", allEntries = true)
	public void delete(int productId) {
		productRepository.delete(productId);
	}

	@Override
	public Product getProduct(int productId) {
		return productRepository.getProduct(productId);
	}

	@Override
	@Cacheable("products")
	public List<Product> getAllProducts() {
		return productRepository.getAllProducts();
	}

	@Override
	@CacheEvict(value = "products", allEntries = true)
	public void evictCache() {}
}

