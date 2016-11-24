package com.mzherdev.salesavingsystem.service;

import com.mzherdev.salesavingsystem.model.Sale;
import com.mzherdev.salesavingsystem.tools.TimeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import static com.mzherdev.salesavingsystem.mock.SaleTestData.*;
import static org.junit.Assert.*;

public class SaleServiceTest extends BaseServiceTest {

    @Autowired
    SaleService service;

    @Before
    public void setUp() {
        service.evictCache();
        jpaUtils.clear2ndLevelHibernateCache();
    }

    @After
    public void tearDown() throws Exception {
        service.evictCache();
    }

    @Test
    public void testAdd() throws Exception {
        Sale sale = new Sale(LocalDateTime.now(), 10.0, 10.0);
        service.add(sale);
        assertEquals(Arrays.asList(sale).size(), service.getAllSales().size());
    }

    @Test
    public void testEdit() throws Exception {
        Sale sale = new Sale(LocalDateTime.now(), 10.0, 10.0);
        service.add(sale);
        assertEquals(ALL.size(), service.getAllSales().size());

        sale = service.getSale(START_ID);
        sale.setCostWithDiscount(9.5);
        service.edit(sale);

        assertEquals(sale.getId(), service.getSale(START_ID).getId());
        assertEquals(sale.getCost(), service.getSale(START_ID).getCost(), 0.01);
        assertEquals(TimeUtils.toString(sale.getDate()), TimeUtils.toString(service.getSale(START_ID).getDate()));
    }

    @Test
    public void testDelete() throws Exception {
        Sale sale = new Sale(LocalDateTime.now(), 10.0, 10.0);
        service.add(sale);

        assertEquals(ALL.size(), service.getAllSales().size());

        service.delete(START_ID);
        assertEquals(Collections.emptyList().size(), service.getAllSales().size());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(UNKNOWN_SALE_ID);
    }

    @Test
    public void testGet() throws Exception {
        service.add(SALE_1);
        assertEquals(SALE_1.getId(), service.getSale(START_ID).getId());
        assertEquals(SALE_1.getCost(), service.getSale(START_ID).getCost(), 0.01);
        assertEquals(TimeUtils.toString(SALE_1.getDate()), TimeUtils.toString(service.getSale(START_ID).getDate()));
    }

    @Test
    public void testGetBetween() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalTime time = dateTime.toLocalTime();
        time = LocalTime.of(time.getHour(), 0);
        LocalDateTime startDateTime = LocalDateTime.of(dateTime.toLocalDate(), time);
        LocalDateTime endDateTime = startDateTime.plusHours(1);

        service.add(SALE_1);
        assertEquals(ALL.size(), service.getBetween(startDateTime, endDateTime).size());
    }

    @Test
    public void testGetNotFound() throws Exception {
        Sale product = service.getSale(UNKNOWN_SALE_ID);
        assertEquals(null, product);
    }

    @Test
    public void testGetAll() throws Exception {
        assertEquals(Collections.EMPTY_LIST, service.getAllSales());
    }


}