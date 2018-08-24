package com.mzherdev.salesavingsystem.repository;

import java.util.List;

import com.mzherdev.salesavingsystem.model.Stats;

public interface StatisticsRepository {
	List<Stats> getStatisticsForCurrentDay();
}
