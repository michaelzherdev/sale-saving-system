package com.mzherdev.salesavingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		Product p = findById(productId);
		if(p != null && !p.getItems().isEmpty()) {
			throw new IllegalArgumentException("You cannot delete this product as you have sale(s) containing this product.");
		}
		productRepository.delete(p);
	}

	@Override
	public Product findById(int productId) {
		return productRepository.findById(productId).orElse(null);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	@Cacheable("products")
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public Product findRandom() {
		return productRepository.findRandom();
	}

	@Override
	@CacheEvict(value = "products", allEntries = true)
	public void evictCache() {}
}

