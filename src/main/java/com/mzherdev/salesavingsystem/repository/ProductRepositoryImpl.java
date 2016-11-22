package com.mzherdev.salesavingsystem.repository;

import com.mzherdev.salesavingsystem.model.Product;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProductRepositoryImpl implements ProductRepository {

    Logger log = LoggerFactory.getLogger(ProductRepositoryImpl.class.getSimpleName());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Product product) {
        sessionFactory.getCurrentSession().save(product);
        log.info("add " + product);
    }

    @Override
    public void edit(Product product) {
        sessionFactory.getCurrentSession().update(product);
        log.info("edit " + product);
    }

    @Override
    public void delete(int productId) {
        Product product = getProduct(productId);
        sessionFactory.getCurrentSession().delete(product);
        log.info("delete " + product);
    }

    @Override
    public Product getProduct(int productId) {
        log.info("getById " + productId);
        return sessionFactory.getCurrentSession().get(Product.class, productId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> getAllProducts() {
        List<Product> result = (List<Product>) sessionFactory.getCurrentSession().createQuery("from Product p order by p.id").list();
        log.info("getAll " + result.size());
        return result;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}