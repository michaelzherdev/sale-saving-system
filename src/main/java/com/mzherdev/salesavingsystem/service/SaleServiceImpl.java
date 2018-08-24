package com.mzherdev.salesavingsystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mzherdev.salesavingsystem.model.Sale;
import com.mzherdev.salesavingsystem.repository.SaleRepository;

@Service("saleService")
@Transactional
public class SaleServiceImpl implements SaleService{
	
	@Autowired
	private SaleRepository saleRepository;

	@Override
	@CacheEvict(value = "sales", allEntries = true)
	public Sale save(Sale sale) {
		return saleRepository.save(sale);
	}

	@Override
	@CacheEvict(value = "sales", allEntries = true)
	public void delete(int saleId) {

		saleRepository.delete(findById(saleId));
	}

	@Override
	public Sale findById(int saleId) {
		return saleRepository.findById(saleId).orElse(null);
	}

	@Override
	public List<Sale> getBetween(LocalDateTime startDate, LocalDateTime endDate) {
		return saleRepository.getBetween(startDate, endDate);
	}

	@Override
	@Cacheable("sales")
	public List<Sale> getAllSales() {
		return saleRepository.findAll();
	}

	@Override
	@CacheEvict(value = "sales", allEntries = true)
	public void evictCache() {}
}
