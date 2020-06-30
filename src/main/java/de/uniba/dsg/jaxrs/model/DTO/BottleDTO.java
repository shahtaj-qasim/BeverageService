package de.uniba.dsg.jaxrs.model.DTO;

import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.model.logic.Order;
import de.uniba.dsg.jaxrs.model.logic.OrderItem;
import de.uniba.dsg.jaxrs.model.logic.OrderStatus;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * DTOs are necessary to hide the inner implementation of your model.
 * You probably change your <@{@link Order} class but this has no effect on your exposed API (non breaking change).
 * If you have no DTO object, you have to redeploy the API and change the version number (this will annoy your customers).
 */

//@XmlRootElement(name = "bottle")
public class BottleDTO {
    private int id;
    private String name;
    private double volume;
    private boolean isAlcoholic;
    private double volumePercent;
    private double price;
    private String supplier;
    private int inStock;

    public BottleDTO(final Bottle bottle) {
        this.id = bottle.getId();
        this.name = bottle.getName();
        this.volume = bottle.getVolume();
        this.isAlcoholic = bottle.getisAlcoholic();
        this.volumePercent = bottle.getVolumePercent();
        this.price = bottle.getPrice();
        this.supplier = bottle.getSupplier();
        this.inStock = bottle.getInStock();
    }

    public Bottle MapToBottle() {
        Bottle bottle = new Bottle();
        bottle.setId(this.id); ;
        bottle.setName(this.name);
        bottle.setVolume(this.volume);
        bottle.setisAlcoholic(this.isAlcoholic);
        bottle.setVolumePercent(this.volumePercent);
        bottle.setPrice(this.price);
        bottle.setSupplier(this.supplier);
        bottle.setInStock(this.inStock);
        return bottle;
    }

    public BottleDTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public boolean getisAlcoholic() {
        return isAlcoholic;
    }

    public void setisAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public double getVolumePercent() {
        return volumePercent;
    }

    public void setVolumePercent(double volumePercent) {
        this.volumePercent = volumePercent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    //    @Override
}