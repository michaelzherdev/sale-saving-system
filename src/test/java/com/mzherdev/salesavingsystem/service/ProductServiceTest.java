package com.mzherdev.salesavingsystem.service;

import com.mzherdev.salesavingsystem.mock.ProductTestData;
import com.mzherdev.salesavingsystem.model.Product;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.mzherdev.salesavingsystem.mock.ProductTestData.*;
import static org.junit.Assert.*;

public class ProductServiceTest extends BaseServiceTest {

    @Autowired
    private ProductService service;

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
        Product product = new Product("NewProduct", 100.0);
        service.add(product);
        assertEquals(ALL.size() + 1, service.getAllProducts().size());
    }

    @Test(expected = ConstraintViolationException.class)
    public void testAddDuplicateName() throws Exception {
        service.add(new Product("Jeans", 9.5));
    }

    @Test
    public void testEdit() throws Exception {
        Product product = service.getProduct(JEANS_PRODUCT_ID);
        product.setName("Jeans NEW");
        product.setPrice(12.0);
        service.edit(product);

        assertEquals(product.getId(), service.getProduct(JEANS_PRODUCT_ID).getId());
        assertEquals(product.getName(), service.getProduct(JEANS_PRODUCT_ID).getName());
        assertEquals(product.getPrice(), service.getProduct(JEANS_PRODUCT_ID).getPrice(), 0.01);
    }

    @Test
    public void testDelete() throws Exception {
        assertEquals(ALL.size(), service.getAllProducts().size());
        service.delete(T_SHIRT_PRODUCT_ID);
        assertEquals(ALL.size() - 1, service.getAllProducts().size());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(UNKNOWN_PRODUCT_ID);
    }

    @Test
    public void testGet() throws Exception {
        Product product = service.getProduct(JEANS_PRODUCT_ID);
        assertEquals(ProductTestData.JEANS.getId(), product.getId());
        assertEquals(ProductTestData.JEANS.getName(), product.getName());
        assertEquals(ProductTestData.JEANS.getPrice(), product.getPrice(), 0.01);
    }

    @Test
    public void testGetNotFound() throws Exception {
        Product product = service.getProduct(UNKNOWN_PRODUCT_ID);
        assertEquals(null, product);
    }

    @Test
    public void testGetAll() throws Exception {
        assertEquals(ALL.size(), service.getAllProducts().size());
    }

}