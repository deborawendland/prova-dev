package com.deborawendland.provadev.service;

import com.deborawendland.provadev.config.AppConfig;
import com.deborawendland.provadev.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ReportServiceTest {

    private Map<Double, Sale> sales;
    private Map<Double, Client> clients;
    private Map<Double, Salesperson> salespeople;

    @Autowired
    private ReportService reportService;

    @Before
    public void init() {
        //reportService = new ReportService();
        sales = new HashMap<>();
        List<SaleItem> saleItemsJohn = new ArrayList<>();
        saleItemsJohn.add(new SaleItem(1, 30, 5.00));
        saleItemsJohn.add(new SaleItem(2, 3, 1.5));
        saleItemsJohn.add(new SaleItem(3, 90, 4.3));
        sales.put((double)2343234, new Sale(23434234, saleItemsJohn, "John"));

        List<SaleItem> saleItemsEmilyFirst = new ArrayList<>();
        saleItemsEmilyFirst.add(new SaleItem(4, 33, 9.40));
        saleItemsEmilyFirst.add(new SaleItem(5, 31, 6.5));
        saleItemsEmilyFirst.add(new SaleItem(6, 9, 1.3));
        saleItemsEmilyFirst.add(new SaleItem(7, 5, 5.3));
        sales.put((double)3452313, new Sale(345, saleItemsEmilyFirst, "Emily"));

        List<SaleItem> saleItemsEmilySecond = new ArrayList<>();
        saleItemsEmilySecond.add(new SaleItem(8, 1, 9.40));
        sales.put((double)7567567, new Sale(7567567, saleItemsEmilySecond, "Emily"));

        List<SaleItem> saleItemsLouis = new ArrayList<>();
        saleItemsLouis.add(new SaleItem(9, 21, 0.40));
        sales.put((double)8978977, new Sale(8978977, saleItemsLouis, "Louis"));

        List<SaleItem> saleItemsMarina = new ArrayList<>();
        saleItemsLouis.add(new SaleItem(10, 69, 0.10));
        sales.put((double)6753453, new Sale(6753453, saleItemsMarina, "Marina"));

        clients = new HashMap<>();
        clients.put((double) 2343,new Client( 2343,"Kyle", "Education"));
        clients.put((double) 2341,new Client( 2341,"Chris", "Sales"));
        clients.put((double) 2342,new Client( 2342,"Paul", "Industry"));
        clients.put((double) 2344,new Client( 2344,"Lana", "Rural"));
        clients.put((double) 2345,new Client( 2344,"Julia", "Finance"));

        salespeople = new HashMap<>();
        salespeople.put((double) 1234, new Salesperson(1234, "John", 40000));
        salespeople.put((double) 1235, new Salesperson(1235, "Emily", 20000));
        salespeople.put((double) 1236, new Salesperson(1236, "Louis", 40500));
        salespeople.put((double) 1237, new Salesperson(1237, "Marina", 35000));
    }

    @Test
    public void generateReport() {
        System.out.println("teste");
        Report report = reportService.generateReport(clients, salespeople, sales);
        Assert.assertEquals(5, report.getClientsQuantity());
        Assert.assertEquals(4, report.getSalespeopleQuantity());
        Assert.assertEquals(3452313, report.getMostExpensiveSaleID(), 0.1);
        Assert.assertEquals("Marina", report.getWorstSalesperson());
    }
}