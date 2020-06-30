package de.uniba.dsg.jaxrs.model.DTO;

import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.logic.BeverageType;
import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.model.logic.Crate;

public class CrateDTO {
    private int id;
    private BottleDTO bottle;
    private int noOfBottles;
    private double price;
    private int inStock;

    public CrateDTO(){

    }

    public CrateDTO(final Crate crate) {
        this.id = crate.getId();
//        Bottle bottle = crate.getBottle();
        this.bottle = new BottleDTO(crate.getBottle());
        this.noOfBottles = crate.getNoOfBottles();
        this.price = crate.getPrice();
        this.inStock = crate.getInStock();
    }

    public Crate MapToCrate() {
        Crate crate = new Crate();
        crate.setId(this.id);
        crate.setBottle(this.bottle.MapToBottle());
        crate.setNoOfBottles(this.noOfBottles);
        crate.setPrice(this.price);
        crate.setInStock(this.inStock);
        return crate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BottleDTO getBottle() {
        return bottle;
    }

    public void setBottle(BottleDTO bottle) {
        this.bottle = bottle;
    }

    public int getNoOfBottles() {
        return noOfBottles;
    }

    public void setNoOfBottles(int noOfBottles) {
        this.noOfBottles = noOfBottles;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

}
