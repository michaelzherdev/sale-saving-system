package com.mzherdev.salesavingsystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mzherdev.salesavingsystem.model.Sale;

public interface SaleService {
	
	Sale save(Sale sale);

	void delete(int saleId);

	Sale findById(int saleId);

	List<Sale> getBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<Sale> getAllSales();

	void evictCache();

	Page<Sale> findAll(Pageable pageable);
}
