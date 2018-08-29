package com.mzherdev.salesavingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mzherdev.salesavingsystem.service.StatisticsService;

@Controller
public class StatsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping(value = "/statistics")
    public String showStatistics(Model model) {
        model.addAttribute("stats", statisticsService.getStatisticsForCurrentDay());
        return "statistics";
    }
}
