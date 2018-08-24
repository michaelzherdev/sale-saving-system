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

//	@Query(value = "SELECT date_part('hour', s.date), count(*) as sales_count , sum(s.cost) as common_cost, avg(s.cost) as avg_cost,\n" +
//			"  sum(s.cost_with_discount) as common_cost_with_discount, avg(s.cost_with_discount) as avg_cost_with_discount,\n" +
//			"  (sum(s.cost) - sum(s.cost_with_discount)) as diff\n" +
//			"FROM sales s\n" +
//			"GROUP BY date_part('hour', s.date)", nativeQuery = true)
//	List<Stats> getStatisticsForCurrentDay();



}
