package com.deborawendland.provadev.model;

public class Salesperson {

    private double cpf;
    private String name;
    private double salary;

    public Salesperson(double cpf, String name, double salary) {
        this.cpf = cpf;
        this.name = name;
        this.salary = salary;
    }

    public double getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }
}
