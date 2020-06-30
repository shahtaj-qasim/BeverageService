package de.uniba.dsg.jaxrs.model.DTO;

import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.logic.BeverageType;
import de.uniba.dsg.jaxrs.model.logic.OrderStatus;

public class BeverageDTO implements Beverage {
    private int id;
    private double price;
    private BeverageType beverageType;

    public BeverageDTO(int id, double price, BeverageType beverageType) { // BeverageType beverageType) {
        this.id = id;
        this.price = price;
        this.beverageType = beverageType;
    }

    public  BeverageDTO(){

    }

    public int getId() { return id; }

    public void setId(int id) {this.id = id;}

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BeverageType getBeverageType() { return beverageType; }

    public void setBeverageType(BeverageType beverageType) { this.beverageType = beverageType; }
}
