package com.mzherdev.salesavingsystem.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Stats {

    private String period;

    private int salesCount;

    private double costCommon;

    private double costAverage;

    private double discountSum;

    private double costCommonWithDiscounts;

    private double costAverageWithDiscounts;

    public Stats() {
    }

    public Stats(String period, int salesCount, double costCommon, double costAverage, double discountSum, double costCommonWithDiscounts, double costAverageWithDiscounts) {
        this.period = period;
        this.salesCount = salesCount;
        this.costCommon = costCommon;
        this.costAverage = costAverage;
        this.discountSum = discountSum;
        this.costCommonWithDiscounts = costCommonWithDiscounts;
        this.costAverageWithDiscounts = costAverageWithDiscounts;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    public double getCostCommon() {
        return costCommon;
    }

    public void setCostCommon(double costCommon) {
        this.costCommon = costCommon;
    }

    public double getCostAverage() {
        return costAverage;
    }

    public void setCostAverage(double costAverage) {
        this.costAverage = costAverage;
    }

    public double getDiscountSum() {
        return new BigDecimal(discountSum).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setDiscountSum(double discountSum) {
        this.discountSum = discountSum;
    }

    public double getCostCommonWithDiscounts() {
        return costCommonWithDiscounts;
    }

    public void setCostCommonWithDiscounts(double costCommonWithDiscounts) {
        this.costCommonWithDiscounts = costCommonWithDiscounts;
    }

    public double getCostAverageWithDiscounts() {
        return costAverageWithDiscounts;
    }

    public void setCostAverageWithDiscounts(double costAverageWithDiscounts) {
        this.costAverageWithDiscounts = costAverageWithDiscounts;
    }
}
