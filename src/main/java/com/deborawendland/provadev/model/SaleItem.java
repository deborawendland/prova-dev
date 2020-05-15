package com.deborawendland.provadev.model;

public class SaleItem {

    private double itemID;
    private int itemQuantity;
    private double itemPrice;

    public SaleItem(double itemID, int itemQuantity, double itemPrice) {
        this.itemID = itemID;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }

    public double getItemID() {
        return itemID;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }
}
