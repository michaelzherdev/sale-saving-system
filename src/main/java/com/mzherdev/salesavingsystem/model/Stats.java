package com.mzherdev.salesavingsystem.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Stats {

    private String period;

    private int salesCount;

    private BigDecimal costCommon;

    private BigDecimal costAverage;

    private BigDecimal discountSum;

    private BigDecimal costCommonWithDiscounts;

    private BigDecimal costAverageWithDiscounts;

    public Stats() {
    }

    public Stats(String period, int salesCount, BigDecimal costCommon, BigDecimal costAverage, BigDecimal discountSum, BigDecimal costCommonWithDiscounts, BigDecimal costAverageWithDiscounts) {
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

    public BigDecimal getCostCommon() {
        return costCommon.setScale(2, RoundingMode.HALF_UP);
    }

    public void setCostCommon(BigDecimal costCommon) {
        this.costCommon = costCommon;
    }

    public BigDecimal getCostAverage() {
        return costAverage.setScale(2, RoundingMode.HALF_UP);
    }

    public void setCostAverage(BigDecimal costAverage) {
        this.costAverage = costAverage;
    }

    public BigDecimal getDiscountSum() {
        return discountSum.setScale(2, RoundingMode.HALF_UP);
    }

    public void setDiscountSum(BigDecimal discountSum) {
        this.discountSum = discountSum;
    }

    public BigDecimal getCostCommonWithDiscounts() {
        return costCommonWithDiscounts.setScale(2, RoundingMode.HALF_UP);
    }

    public void setCostCommonWithDiscounts(BigDecimal costCommonWithDiscounts) {
        this.costCommonWithDiscounts = costCommonWithDiscounts;
    }

    public BigDecimal getCostAverageWithDiscounts() {
        return costAverageWithDiscounts.setScale(2, RoundingMode.HALF_UP);
    }

    public void setCostAverageWithDiscounts(BigDecimal costAverageWithDiscounts) {
        this.costAverageWithDiscounts = costAverageWithDiscounts;
    }
}
