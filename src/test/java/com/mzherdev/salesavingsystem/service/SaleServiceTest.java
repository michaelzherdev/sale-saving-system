package com.mzherdev.salesavingsystem.service;

import static com.mzherdev.salesavingsystem.mock.SaleTestData.ALL;
import static com.mzherdev.salesavingsystem.mock.SaleTestData.SALE_1;
import static com.mzherdev.salesavingsystem.mock.SaleTestData.START_ID;
import static com.mzherdev.salesavingsystem.mock.SaleTestData.UNKNOWN_SALE_ID;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.mzherdev.salesavingsystem.model.Sale;
import com.mzherdev.salesavingsystem.tools.TimeUtils;

public class SaleServiceTest extends BaseServiceTest {

    @Autowired
    SaleService service;

    @Before
    public void setUp() {
        service.evictCache();
    }

    @After
    public void tearDown() throws Exception {
        service.evictCache();
    }

    @Test
    public void testAdd() throws Exception {
        Sale sale = new Sale(LocalDateTime.now(), BigDecimal.TEN, BigDecimal.TEN);
        service.save(sale);
        assertEquals(Arrays.asList(sale).size(), service.getAllSales().size());
    }

    @Test
    public void testEdit() throws Exception {
        Sale sale = new Sale(LocalDateTime.now(), BigDecimal.TEN, BigDecimal.TEN);
        service.save(sale);
        assertEquals(ALL.size(), service.getAllSales().size());

        sale = service.findById(START_ID);
        sale.setCostWithDiscount(new BigDecimal(9.5));
        service.save(sale);

        assertEquals(sale.getId(), service.findById(START_ID).getId());
        assertEquals(sale.getCost().doubleValue(), service.findById(START_ID).getCost().doubleValue(), new BigDecimal(0.01).doubleValue());
        assertEquals(TimeUtils.toString(sale.getDate()), TimeUtils.toString(service.findById(START_ID).getDate()));
    }

    @Test
    public void testDelete() throws Exception {
        Sale sale = new Sale(LocalDateTime.now(), BigDecimal.TEN, BigDecimal.TEN);
        service.save(sale);

        assertEquals(ALL.size(), service.getAllSales().size());

        service.delete(START_ID);
        assertEquals(Collections.emptyList().size(), service.getAllSales().size());

    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(UNKNOWN_SALE_ID);
    }

    @Test
    public void testGet() throws Exception {
        service.save(SALE_1);
        assertEquals(SALE_1.getId(), service.findById(START_ID).getId());
        assertEquals(SALE_1.getCost().doubleValue(), service.findById(START_ID).getCost().doubleValue(), new BigDecimal(0.01).doubleValue());
        assertEquals(TimeUtils.toString(SALE_1.getDate()), TimeUtils.toString(service.findById(START_ID).getDate()));
    }

    @Test
    public void testGetBetween() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime time = dateTime.toLocalTime();
        time = LocalTime.of(time.getHour(), 0);
        LocalDateTime startDateTime = LocalDateTime.of(dateTime.toLocalDate(), time);
        LocalDateTime endDateTime = startDateTime.plusHours(1);

        service.save(SALE_1);
        assertEquals(ALL.size(), service.getBetween(startDateTime, endDateTime).size());
    }

    @Test
    public void testGetNotFound() throws Exception {
        Sale product = service.findById(UNKNOWN_SALE_ID);
        assertEquals(null, product);
    }

    @Test
    public void testGetAll() throws Exception {
        assertEquals(Collections.EMPTY_LIST, service.getAllSales());
    }


}