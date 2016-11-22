package com.mzherdev.salesavingsystem.controller;

import com.mzherdev.salesavingsystem.model.Sale;
import com.mzherdev.salesavingsystem.model.Stats;
import com.mzherdev.salesavingsystem.service.SaleService;
import com.mzherdev.salesavingsystem.tools.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StatsController {

    @Autowired
    private SaleService saleService;

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public String showStatistics(Model model) {
        List<Stats> statsList = new ArrayList<>();

        List<Sale> sales = saleService.getAllSales();

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.of(8, 0, 0);
        LocalDateTime startDateTime = LocalDateTime.of(date, time);
        LocalDateTime endDateTime = startDateTime.plusHours(1);
        LocalDateTime finishDateTime = startDateTime.plusDays(1);

        model.addAttribute("today", TimeUtils.toString(startDateTime.toLocalDate()));

        do {
            LocalDateTime startDate = startDateTime, endDate = endDateTime;

            List<Sale> salesForHour = sales.stream()
                    .filter(s -> TimeUtils.isBetween(s.getDate(), startDate, endDate))
                    .collect(Collectors.toList());

            String period = String.format("%s - %s", TimeUtils.toString(startDateTime.toLocalTime()),
                    TimeUtils.toString(endDateTime.toLocalTime()));
            int salesCount = salesForHour.size();
            double costCommon = salesForHour.stream().mapToDouble(s -> s.getCost()).sum();
            double costAverage = salesCount != 0 ? costCommon / salesCount : 0;
            double costCommonWithDiscounts = salesForHour.stream().mapToDouble(s -> s.getCostWithDiscount()).sum();
            double costAverageWithDiscounts = salesCount != 0 ? costCommonWithDiscounts / salesCount : 0;
            double discountSum = costCommon - costCommonWithDiscounts;

            Stats stats = new Stats(period, salesCount, costCommon, costAverage, discountSum, costCommonWithDiscounts, costAverageWithDiscounts);
            statsList.add(stats);

            startDateTime = endDateTime;
            endDateTime = startDateTime.plusHours(1);
        } while (!TimeUtils.isEqual(startDateTime.toLocalDate(), finishDateTime.toLocalDate()));

        model.addAttribute("stats", statsList);
        return "stats/statistics";
    }
}
