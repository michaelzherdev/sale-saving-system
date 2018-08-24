package com.mzherdev.salesavingsystem.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mzherdev.salesavingsystem.model.Stats;

@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Stats> getStatisticsForCurrentDay() {
		String sql = "SELECT date_part('hour', s.date) || '-' || date_part('hour', s.date) + 1 as period," +
				" count(*) as sales_count , sum(s.cost) as cost_common, avg(s.cost) as cost_average," +
				" sum(s.cost_with_discount) as cost_common_with_discounts, avg(s.cost_with_discount) as cost_average_with_discounts," +
				" (sum(s.cost) - sum(s.cost_with_discount)) as discount_sum" +
				" FROM sales s" +
				" WHERE  date_part('day', s.date) = date_part('day', now())" +
				" GROUP BY date_part('hour', s.date)";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Stats.class));
	}
}
