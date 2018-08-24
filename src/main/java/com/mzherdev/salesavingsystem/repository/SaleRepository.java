package com.mzherdev.salesavingsystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mzherdev.salesavingsystem.model.Sale;
import com.mzherdev.salesavingsystem.model.Stats;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
	@Query("SELECT s FROM Sale s WHERE s.date BETWEEN ?1 AND ?2 ORDER BY s.date DESC")
	List<Sale> getBetween(LocalDateTime startDate, LocalDateTime endDate);
}
