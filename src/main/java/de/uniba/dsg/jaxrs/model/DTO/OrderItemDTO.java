package de.uniba.dsg.jaxrs.model.DTO;

import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.logic.BeverageType;
import de.uniba.dsg.jaxrs.model.logic.OrderItem;

public class OrderItemDTO {
    private int number;
    private BeverageDTO beverage;
    private int quantity;

    public OrderItemDTO(OrderItem orderItem) {
        this.number = orderItem.getNumber();
        Beverage beverage = orderItem.getBeverage();
        this.beverage = new BeverageDTO(beverage.getId(),beverage.getPrice(),beverage.getBeverageType());
        this.quantity = orderItem.getQuantity();
    }
    public OrderItemDTO(){

    }
    public OrderItem MapToOrderItem()  {
        OrderItem orderItem = new OrderItem();
        orderItem.setNumber(this.number);
        orderItem.setBeverage((Beverage) this.beverage);
        orderItem.setQuantity(this.quantity);
        return orderItem;
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BeverageDTO getBeverage() {
        return beverage;
    }

    public void setBeverage(BeverageDTO beverage) {
        this.beverage = beverage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "number=" + number +
                ", beverage=" + beverage +
                ", quantity=" + quantity +
                '}';
    }
}
