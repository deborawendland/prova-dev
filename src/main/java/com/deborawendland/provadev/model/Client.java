package com.deborawendland.provadev.model;

public class Client {

    private double cnpj;
    private String name;
    private String businessArea;

    public Client(double cnpj, String name, String businessArea) {
        this.cnpj = cnpj;
        this.name = name;
        this.businessArea = businessArea;
    }

    public double getCnpj() {
        return cnpj;
    }

    public String getName() {
        return name;
    }

    public String getBusinessArea() {
        return businessArea;
    }
}
