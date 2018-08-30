package com.mzherdev.salesavingsystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mzherdev.salesavingsystem.model.OrderItem;
import com.mzherdev.salesavingsystem.model.Sale;
import com.mzherdev.salesavingsystem.repository.OrderItemRepository;
import com.mzherdev.salesavingsystem.repository.SaleRepository;

@Service("saleService")
@Transactional
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleRepository saleRepository;

	@Autowired
	private OrderItemRepository itemRepository;

	@Override
	@CacheEvict(value = "sales", allEntries = true)
	public Sale save(Sale sale) {
		saleRepository.save(sale);
		List<OrderItem> items = new CopyOnWriteArrayList<>(sale.getItems());
		items.forEach( oi -> oi.setSale(sale));
		itemRepository.saveAll(items);
		return sale;
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
	public void evictCache() {
	}

	@Override
	@Cacheable("sales")
	public Page<Sale> findAll(Pageable pageable) {
		return saleRepository.findAll(pageable);
	}
}
