package com.mzherdev.salesavingsystem.tools;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceContext;


public class JpaUtils {

    @Autowired
    private SessionFactory sessionFactory;

    public void clear2ndLevelHibernateCache() {
        sessionFactory.getCache().evictQueryRegions();
        sessionFactory.getCache().evictDefaultQueryRegion();
        sessionFactory.getCache().evictCollectionRegions();
        sessionFactory.getCache().evictEntityRegions();
    }
}
