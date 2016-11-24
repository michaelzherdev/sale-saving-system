package com.mzherdev.salesavingsystem.mock;

import com.mzherdev.salesavingsystem.model.Sale;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SaleTestData {

    private SaleTestData(){}

    public static final int START_ID = 1;
    public static final int UNKNOWN_SALE_ID = 100;

    public static final Sale SALE_1 = new Sale(LocalDateTime.now(), 9.5, 9.5);

    public static final List<Sale> ALL = new ArrayList<>();

    static {
        ALL.add(SALE_1);
    }
}
