package com.mzherdev.salesavingsystem.service;


import com.mzherdev.salesavingsystem.config.WebConfig;
import com.mzherdev.salesavingsystem.tools.JpaUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class}, loader = AnnotationConfigWebContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:db/init_postgres_db.sql", "classpath:db/fill_in_postgres_db.sql"},
        config = @SqlConfig(encoding = "UTF-8"),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class BaseServiceTest {

    @Autowired
    protected JpaUtils jpaUtils;
}
