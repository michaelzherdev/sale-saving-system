package com.mzherdev.salesavingsystem.repository;

import com.mzherdev.salesavingsystem.model.Discount;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DiscountRepositoryImpl implements DiscountRepository {

    Logger log = LoggerFactory.getLogger(DiscountRepositoryImpl.class.getSimpleName());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(Discount discount) {
        sessionFactory.getCurrentSession().save(discount);
        log.info("add " + discount);
    }

    @Override
    public void edit(Discount discount) {
        sessionFactory.getCurrentSession().update(discount);
        log.info("edit " + discount);
    }

    @Override
    public Discount getDiscount(int discountId) {
        log.info("getById " + discountId);
        return sessionFactory.getCurrentSession().get(Discount.class, discountId);
    }

    @Override
    public List<Discount> getAllDiscounts() {
        List<Discount> result = (List<Discount>) sessionFactory.getCurrentSession().createQuery("FROM Discount d ORDER BY d.timeEnd DESC").list();
        log.info("getAll " + result.size());
        return result;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
