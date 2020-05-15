package com.deborawendland.provadev;

import com.deborawendland.provadev.config.AppConfig;
import com.deborawendland.provadev.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@SpringBootApplication
public class MyApplication /*implements CommandLineRunner */{

/*    @Autowired
    private SalesService salesService;*/

    public static void main(String[] args) {
        /*SpringApplication app = new SpringApplication(MyApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);*/
/*        SalesService salesService = new SalesService();
        salesService.parseFile("001ç1234567891234çPedroç50000\n" +
                "001ç3245678865434çPauloç40000.99\n" +
                "002ç2345675434544345çJose da SilvaçRural\n" +
                "002ç2345675433444345çEduardo PereiraçRural\n" +
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro\n" +
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedroçoi\n" +
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");*/

/*        System.out.println("Clients quantity: " + salesService.reportClientsQuantity());
        System.out.println("Salespeople quantity: " + salesService.reportSalespeopleQuantity());
        System.out.println("Worst salesperson: " + salesService.reportWorstSalesperson());
        System.out.println("Most expensive sale: " + salesService.reportMostExpensiveSaleID());*/

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        SalesService salesService = (SalesService) applicationContext.getBean("salesService");
        //salesService.getSalesInfo();
        salesService.getSalesInfo();
        //salesService.createReport();
        ((AnnotationConfigApplicationContext)applicationContext).close();
    }

   /* @Override
    public void run(String... args) throws Exception {
        salesService.getSalesInfo();
        salesService.createReport();
    }*/
}
