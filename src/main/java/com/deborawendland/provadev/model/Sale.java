package com.deborawendland.provadev.model;

import java.util.List;

public class Sale {

    private double saleID;
    private List<SaleItem> saleItems;
    private String salesperson;

    public Sale(double saleID, List<SaleItem> saleItems, String salesperson) {
        this.saleID = saleID;
        this.saleItems = saleItems;
        this.salesperson = salesperson;
    }

    public double getSaleID() {
        return saleID;
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public String getSalesperson() {
        return salesperson;
    }
}
