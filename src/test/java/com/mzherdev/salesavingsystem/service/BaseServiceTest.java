package com.mzherdev.salesavingsystem.service;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = {"classpath:db/init_postgres_db.sql", "classpath:db/fill_in_postgres_db.sql"},
		config = @SqlConfig(encoding = "UTF-8"),
		executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class BaseServiceTest {
}
