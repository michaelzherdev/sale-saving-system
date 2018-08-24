package com.mzherdev.salesavingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mzherdev.salesavingsystem.model.Product;
import com.mzherdev.salesavingsystem.repository.ProductRepository;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	@CacheEvict(value = "products", allEntries = true)
	public void save(Product product) {
		productRepository.save(product);
	}

	@Override
	@CacheEvict(value = "products", allEntries = true)
	public void delete(int productId) {
		productRepository.delete(findById(productId));
	}

	@Override
	public Product findById(int productId) {
		return productRepository.findById(productId).orElse(null);
	}

	@Override
	@Cacheable("products")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product findRandom() {
		return productRepository.findRandom();
	}

	@Override
	@CacheEvict(value = "products", allEntries = true)
	public void evictCache() {}
}

