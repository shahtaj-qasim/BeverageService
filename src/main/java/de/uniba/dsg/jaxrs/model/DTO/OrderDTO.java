package de.uniba.dsg.jaxrs.model.DTO;

import de.uniba.dsg.jaxrs.model.logic.OrderItem;
import de.uniba.dsg.jaxrs.model.logic.OrderStatus;
import de.uniba.dsg.jaxrs.model.logic.Order;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DTOs are necessary to hide the inner implementation of your model.
 * You probably change your <@{@link Order} class but this has no effect on your exposed API (non breaking change).
 * If you have no DTO object, you have to redeploy the API and change the version number (this will annoy your customers).
 */
@XmlRootElement(name = "order")
public class OrderDTO {
    private int orderId;
    private List<OrderItemDTO> orderItems;
    private double price;
    private OrderStatus status;

    public  OrderDTO(){
    }

    public OrderDTO(final Order order) {
        this.orderId = order.getOrderId();
        List<OrderItem> items = order.getOrderItems();
        this.orderItems = new ArrayList<OrderItemDTO>();
        for (final OrderItem orderItem : items) {
            this.orderItems.add(new OrderItemDTO(orderItem));
        }
        this.price = order.getPrice();
        this.status = order.getStatus();
    }

    public Order MapToOrder() {
        Order order = new Order();
        order.setOrderId(this.orderId) ;
        order.setPrice(this.price);
        order.setStatus(this.status);
        List<OrderItem> items = new ArrayList<OrderItem>();
        for (final OrderItemDTO orderItemDto : this.orderItems) {
            items.add(orderItemDto.MapToOrderItem());
        }
        order.setOrderItems(items);
        return order;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + this.orderId +
                ", positions='" + this.orderItems + '\'' +
                ", price=" + this.price +
                ", status='" + this.status + '\'' +
                '}';
    }
}

