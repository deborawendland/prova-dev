package com.deborawendland.provadev.service;

import com.deborawendland.provadev.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportService {

    public Report generateReport(Map<Double, Client> clients, Map<Double, Salesperson> salespeople, Map<Double, Sale> sales){
        return new Report(clients.size(), salespeople.size(), reportWorstSalesperson(sales), reportMostExpensiveSaleID(sales));
    }

    private double reportMostExpensiveSaleID (Map<Double, Sale> sales) {
        AtomicReference<Double> mostExpensiveSaleID = new AtomicReference<>((double) 0);
        AtomicReference<Double> mostExpensiveSalePrice = new AtomicReference<>((double) 0);
        sales.forEach(
                (id, sale) -> {
                    double salePrice = sale.getSaleItems().stream().mapToDouble(saleItem -> saleItem.getItemQuantity() * saleItem.getItemPrice()).sum();
                    if (salePrice > mostExpensiveSalePrice.get()) {
                        mostExpensiveSaleID.set(id);
                        mostExpensiveSalePrice.set(salePrice);
                    }
                }
        );
        return mostExpensiveSaleID.get();
    }

    private String reportWorstSalesperson (Map<Double, Sale> sales) {
        Map<String, Double> salesBySalesPerson = new HashMap<>();
       sales.forEach(
                (id, sale) -> {
                    double salePrice = sale.getSaleItems().stream().mapToDouble(saleItem -> saleItem.getItemQuantity() * saleItem.getItemPrice()).sum();
                    salesBySalesPerson.put(sale.getSalesperson(), salesBySalesPerson.getOrDefault(sale.getSalesperson(), 0.0) + salePrice);
                }
        );
        return Collections.min(salesBySalesPerson.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}


