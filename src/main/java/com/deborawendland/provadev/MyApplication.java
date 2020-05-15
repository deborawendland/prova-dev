package com.deborawendland.provadev;

import com.deborawendland.provadev.config.AppConfig;
import com.deborawendland.provadev.service.SalesService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        SalesService salesService = (SalesService) applicationContext.getBean("salesService");
        salesService.getSalesInfo();
        ((AnnotationConfigApplicationContext)applicationContext).close();
    }

}