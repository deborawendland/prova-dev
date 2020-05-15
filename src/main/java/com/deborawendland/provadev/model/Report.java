package com.deborawendland.provadev.model;

public class Report {

    private int clientsQuantity;
    private int salespeopleQuantity;
    private String worstSalesperson;
    private double mostExpensiveSaleID;

    public Report(int clientsQuantity, int salespeopleQuantity, String worstSalesperson, double mostExpensiveSaleID) {
        this.clientsQuantity = clientsQuantity;
        this.salespeopleQuantity = salespeopleQuantity;
        this.worstSalesperson = worstSalesperson;
        this.mostExpensiveSaleID = mostExpensiveSaleID;
    }

    public int getClientsQuantity() {
        return clientsQuantity;
    }

    public int getSalespeopleQuantity() {
        return salespeopleQuantity;
    }

    public String getWorstSalesperson() {
        return worstSalesperson;
    }

    public double getMostExpensiveSaleID() {
        return mostExpensiveSaleID;
    }
}
