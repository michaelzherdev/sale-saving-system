package com.mzherdev.salesavingsystem.config;

import com.mzherdev.salesavingsystem.tools.JpaUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement // tx:annotation-driven
@EnableWebMvc
@ComponentScan({"com.mzherdev.salesavingsystem"})
@PropertySource("classpath:db/db.properties")
@EnableCaching
@EnableScheduling
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    Environment environment;

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    @Bean
    public BasicDataSource dataSource()  {
        URI dbUri = null;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());

        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.setProperty("dialect", environment.getProperty("db.dialect"));
        configuration.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("db.hbm2ddl.auto"));
        configuration.setProperty("show_sql", environment.getProperty("db.show_sql"));

        configuration.setProperty("hibernate.cache.region.factory_class", environment.getProperty("db.cache.region.factory_class"));
        configuration.setProperty("hibernate.cache.use_second_level_cache", environment.getProperty("db.cache.use_second_level_cache"));
        configuration.setProperty("hibernate.cache.use_query_cache", environment.getProperty("db.cache.use_query_cache"));
        configuration.setProperty("net.sf.ehcache.configurationResourceName", environment.getProperty("db.ehcache.configurationResourceName"));

        sessionFactoryBean.setHibernateProperties(configuration.getProperties());
        sessionFactoryBean.setPackagesToScan("com.mzherdev.salesavingsystem.model");
        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager() throws SQLException {
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(sessionFactory().getObject());
        return manager;
    }

    // cache
    @Bean
    CacheManager cacheManager(){
        return new EhCacheCacheManager(ehCacheManagerFactoryBean().getObject());
    }

    @Bean
    EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
        bean.setConfigLocation(new ClassPathResource("cache/ehcache.xml"));
        bean.setShared(true);
        return bean;
    }

    @Bean
    JpaUtils jpaUtils() {
        return new JpaUtils();
    }
}