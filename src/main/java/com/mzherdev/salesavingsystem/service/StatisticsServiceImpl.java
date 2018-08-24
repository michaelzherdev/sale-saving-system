package com.mzherdev.salesavingsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mzherdev.salesavingsystem.model.Stats;
import com.mzherdev.salesavingsystem.repository.StatisticsRepository;

@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	StatisticsRepository statisticsRepository;

	@Override
	public List<Stats> getStatisticsForCurrentDay() {
		return statisticsRepository.getStatisticsForCurrentDay();
	}
}
