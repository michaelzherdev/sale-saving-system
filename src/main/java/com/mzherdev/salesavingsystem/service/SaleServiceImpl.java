package com.mzherdev.salesavingsystem.service;

import com.mzherdev.salesavingsystem.repository.SaleRepository;
import com.mzherdev.salesavingsystem.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService{
	
	@Autowired
	private SaleRepository saleRepository;

	@Override
	@CacheEvict(value = "sales", allEntries = true)
	public Sale add(Sale sale) {
		return saleRepository.add(sale);
	}

	@Override
	@CacheEvict(value = "sales", allEntries = true)
	public void edit(Sale sale) {
		saleRepository.edit(sale);
	}

	@Override
	@CacheEvict(value = "sales", allEntries = true)
	public void delete(int saleId) {
		saleRepository.delete(saleId);
	}

	@Override
	public Sale getSale(int saleId) {
		return saleRepository.getSale(saleId);
	}

	@Override
	public List<Sale> getBetween(LocalDateTime startDate, LocalDateTime endDate) {
		return saleRepository.getBetween(startDate, endDate);
	}

	@Override
	public Sale getEarliestSale() {
		return saleRepository.getEarliestSale();
	}

	@Override
	@Cacheable("sales")
	public List<Sale> getAllSales() {
		return saleRepository.getAllSales();
	}

	@Override
	@CacheEvict(value = "sales", allEntries = true)
	public void evictCache() {}
}
