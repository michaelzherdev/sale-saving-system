package com.mzherdev.salesavingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mzherdev.salesavingsystem.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query(value = "SELECT * FROM products ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Product findRandom();
}
