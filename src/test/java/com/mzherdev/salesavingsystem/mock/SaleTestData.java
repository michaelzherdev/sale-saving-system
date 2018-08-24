package com.mzherdev.salesavingsystem.mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mzherdev.salesavingsystem.model.Sale;

public class SaleTestData {

    private SaleTestData(){}

    public static final int START_ID = 1;
    public static final int UNKNOWN_SALE_ID = 100;

    public static final Sale SALE_1 = new Sale(LocalDateTime.now(), BigDecimal.valueOf(9.5), BigDecimal.valueOf(9.5));

    public static final List<Sale> ALL = new ArrayList<>();

    static {
        ALL.add(SALE_1);
    }
}
