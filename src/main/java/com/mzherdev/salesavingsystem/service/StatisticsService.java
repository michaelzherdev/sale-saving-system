package com.mzherdev.salesavingsystem.service;

import java.util.List;

import com.mzherdev.salesavingsystem.model.Stats;

public interface StatisticsService {

	List<Stats> getStatisticsForCurrentDay();
}
