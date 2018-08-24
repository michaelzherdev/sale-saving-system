package com.mzherdev.salesavingsystem.service;

import java.time.LocalDateTime;
import java.util.List;

import com.mzherdev.salesavingsystem.model.Sale;
import com.mzherdev.salesavingsystem.model.Stats;

public interface SaleService {
	
	Sale save(Sale sale);

	void delete(int saleId);

	Sale findById(int saleId);

	List<Sale> getBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<Sale> getAllSales();

	void evictCache();
}
