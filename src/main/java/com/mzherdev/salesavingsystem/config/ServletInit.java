package com.mzherdev.salesavingsystem.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Arrays;

public class ServletInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    Logger log = LoggerFactory.getLogger(ServletInit.class.getSimpleName());

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.scan("com.mzherdev.salesavingsystem.config");
        ctx.setServletContext(servletContext);
        ctx.refresh();
        log.info("BeanDefinitionNames: " + Arrays.toString(ctx.getBeanDefinitionNames()));

//        to 'glue' WebApplicationContext to the lifecycle of ServletContext
        servletContext.addListener(new ContextLoaderListener(ctx));

        servletContext.addListener(new RequestContextListener());

        ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        dynamic.addMapping("/");
        dynamic.setLoadOnStartup(1);
    }
}
