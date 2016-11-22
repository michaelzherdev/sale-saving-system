package com.mzherdev.salesavingsystem.repository;


import com.mzherdev.salesavingsystem.model.OrderItem;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class OrderItemRepositoryImpl implements OrderItemRepository {

    Logger log = LoggerFactory.getLogger(OrderItemRepositoryImpl.class.getSimpleName());

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(OrderItem orderItem) {
        sessionFactory.getCurrentSession().save(orderItem);
        log.info("add " + orderItem);
    }

    @Override
    public void edit(OrderItem orderItem) {
        sessionFactory.getCurrentSession().update(orderItem);
        log.info("edit " + orderItem);
    }

    @Override
    public OrderItem getOrderItem(int orderItemId) {
        log.info("getById " + orderItemId);
        return sessionFactory.getCurrentSession().get(OrderItem.class, orderItemId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OrderItem> getAllOrderItems() {
        List<OrderItem> result = (List<OrderItem>) sessionFactory.getCurrentSession().createQuery("FROM OrderItem").list();
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
