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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = setH2Period(period);
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

    private String setH2Period(String per) {
        String res = per;
        if(per.length() == 2) {
            try {
                int endHour = Integer.parseInt(per) + 1;
                res += ":00 - " + endHour +":00";
            } catch (NumberFormatException e) { }
        }
        return res;
    }
}
