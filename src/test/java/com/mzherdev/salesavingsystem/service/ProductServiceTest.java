package com.mzherdev.salesavingsystem.service;

import static com.mzherdev.salesavingsystem.mock.ProductTestData.ALL;
import static com.mzherdev.salesavingsystem.mock.ProductTestData.JEANS_PRODUCT_ID;
import static com.mzherdev.salesavingsystem.mock.ProductTestData.T_SHIRT_PRODUCT_ID;
import static com.mzherdev.salesavingsystem.mock.ProductTestData.UNKNOWN_PRODUCT_ID;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.mzherdev.salesavingsystem.mock.ProductTestData;
import com.mzherdev.salesavingsystem.model.Product;

public class ProductServiceTest extends BaseServiceTest {

    @Autowired
    private ProductService service;

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
        Product product = new Product("NewProduct", BigDecimal.valueOf(100.0));
        service.save(product);
        assertEquals(ALL.size() + 1, service.getAllProducts().size());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testAddDuplicateName() throws Exception {
        service.save(new Product("Jeans", BigDecimal.valueOf(9.5)));
    }

    @Test
    public void testEdit() throws Exception {
        Product product = service.findById(JEANS_PRODUCT_ID);
        product.setName("Jeans NEW");
        product.setPrice(BigDecimal.valueOf(12.0));
        service.save(product);

        assertEquals(product.getId(), service.findById(JEANS_PRODUCT_ID).getId());
        assertEquals(product.getName(), service.findById(JEANS_PRODUCT_ID).getName());
        assertEquals(product.getPrice().doubleValue(), service.findById(JEANS_PRODUCT_ID).getPrice().doubleValue(), 0.01);
    }

    @Test
    public void testDelete() throws Exception {
        assertEquals(ALL.size(), service.getAllProducts().size());
        service.delete(T_SHIRT_PRODUCT_ID);
        assertEquals(ALL.size() - 1, service.getAllProducts().size());

    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(UNKNOWN_PRODUCT_ID);
    }

    @Test
    public void testGet() throws Exception {
        Product product = service.findById(JEANS_PRODUCT_ID);
        assertEquals(ProductTestData.JEANS.getId(), product.getId());
        assertEquals(ProductTestData.JEANS.getName(), product.getName());
        assertEquals(ProductTestData.JEANS.getPrice().doubleValue(), product.getPrice().doubleValue(), 0.01);
    }

    @Test
    public void testGetNotFound() throws Exception {
        Product product = service.findById(UNKNOWN_PRODUCT_ID);
        assertEquals(null, product);
    }

    @Test
    public void testGetAll() throws Exception {
        assertEquals(ALL.size(), service.getAllProducts().size());
    }

}