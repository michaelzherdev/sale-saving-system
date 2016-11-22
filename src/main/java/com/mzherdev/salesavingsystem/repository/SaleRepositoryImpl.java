package com.mzherdev.salesavingsystem.repository;

import com.mzherdev.salesavingsystem.model.Sale;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class SaleRepositoryImpl implements SaleRepository {

    Logger log = LoggerFactory.getLogger(SaleRepositoryImpl.class.getSimpleName());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Sale add(Sale sale) {
        sessionFactory.getCurrentSession().save(sale);
        log.info("add " + sale);
        return sale;
    }

    @Override
    public void edit(Sale sale) {
        sessionFactory.getCurrentSession().update(sale);
        log.info("edit " + sale);
    }

    @Override
    public void delete(int saleId) {
        Sale sale = getSale(saleId);
        sessionFactory.getCurrentSession().delete(sale);
        log.info("delete " + sale);
    }

    @Override
    public Sale getSale(int saleId) {
        log.info("getById " + saleId);
        return sessionFactory.getCurrentSession().get(Sale.class, saleId);
    }

    @Override
    public List<Sale> getBetween(LocalDateTime startDate, LocalDateTime endDate) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Sale.class);
//        criteria.add(Restrictions.ge("date", startDate));
//        criteria.add(Restrictions.le("date", endDate));
        criteria.add(Restrictions.between("date", startDate, endDate));
        criteria.addOrder(Order.desc("date"));
//        sessionFactory.getCurrentSession()
//                .createQuery("FROM Sale s WHERE s.date BETWEEN :startDate AND :endDate ORDER BY s.date DESC")
//                .setParameter("startDate", startDate)
//                .setParameter("endDate", endDate);
        return criteria.list();
    }

    @Override
    public Sale getEarliestSale() {
        return (Sale) sessionFactory.getCurrentSession().createQuery("FROM Sale s ORDER BY s.date ASC LIMIT 1").uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Sale> getAllSales() {
        List<Sale> result = sessionFactory.getCurrentSession().createQuery("FROM Sale s ORDER BY s.date DESC").list();
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
