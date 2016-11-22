package com.mzherdev.salesavingsystem.repository;

import com.mzherdev.salesavingsystem.model.Sale;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository {

	Sale add(Sale sale);

	void edit(Sale sale);

	void delete(int saleId);

	Sale getSale(int saleId);

	List<Sale> getBetween(LocalDateTime startDate, LocalDateTime endDate);

	Sale getEarliestSale();

	List<Sale> getAllSales();

}
