package de.uniba.dsg.jaxrs.Services;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.Beverage;
import de.uniba.dsg.jaxrs.model.DTO.BeverageDTO;
import de.uniba.dsg.jaxrs.model.DTO.OrderItemDTO;
import de.uniba.dsg.jaxrs.model.logic.*;

import java.util.Comparator;

public class OrderService {

    public static final OrderService instance = new OrderService();

    private final DB db;

    public OrderService() {
        this.db = new DB();
    }

    public int orderStockValue;

    /**
     * Get order by Id
     * @author Shavez Hameed
     * @return Order
     */
    public Order getOrderById(final int orderId) {
        return this.db.orders.stream().filter(c -> c.getOrderId() == orderId).findFirst().orElse(null);
    }

    //POST

    /**
     * Place a new order to the memory database :)
     * @author Shavez Hameed
     * @param order
     * @return Order Details
     */

    public boolean isOrderValid(final Order order) {
        for (final OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getQuantity() <= 0) {
                return false;
            }
            Beverage beverage = orderItem.getBeverage();
            if (beverage.getBeverageType() == BeverageType.BOTTLE) {
                Bottle bottle = this.db.bottles.get(beverage.getId() - 1);
                int inStock = bottle.getInStock();
                if (inStock - orderItem.getQuantity() < 0) {
                    return false;
                }
            }
            if (beverage.getBeverageType() == BeverageType.CRATE) {
                Crate crate = this.db.crates.get(beverage.getId() - 1);
                int inStock = crate.getInStock();
                if (inStock - orderItem.getQuantity() < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public Order submitOrder(final Order order) {
        if (this.isOrderValid(order)) {
            for (final OrderItem orderItem : order.getOrderItems()) {
                Beverage beverage = orderItem.getBeverage();
                if (beverage.getBeverageType() == BeverageType.BOTTLE) {
                    Bottle bottle = this.db.bottles.get(beverage.getId() - 1);
                    int inStock = bottle.getInStock();
                    bottle.setInStock(inStock - orderItem.getQuantity());
                }

                if (beverage.getBeverageType() == BeverageType.CRATE) {
                    Crate crate = this.db.crates.get(beverage.getId() - 1);
                    int inStock = crate.getInStock();
                    crate.setInStock(inStock - orderItem.getQuantity());
                }
            }
            order.setOrderId(this.db.orders.stream().map(Order::getOrderId).max(Comparator.naturalOrder()).orElse(0) + 1);
            this.db.orders.add(order);
            order.setStatus(OrderStatus.SUBMITTED);
            return order;
        }
        return null;
    }
    //PUT

    /**
     * Update an existing order.
     * @author: Deepika Arneja
     * @param orderCheck //     * @param orderId
     * @return True or False
     */
    public boolean isOrderUpdateValid(final Order orderCheck, Order dbOrder) {
        for (final OrderItem orderItem : orderCheck.getOrderItems()) {
            if (orderItem.getQuantity() <= 0) {
                return false;
            }
            Beverage beverage = orderItem.getBeverage();
            if (beverage.getBeverageType() == BeverageType.BOTTLE) {
                Bottle bottle = this.db.bottles.get(beverage.getId() - 1);
                int inStock = bottle.getInStock();
                boolean isOld = false;
                for (final OrderItem dbOrderItem : dbOrder.getOrderItems()) {
                    if (dbOrderItem.getBeverage().getBeverageType() == BeverageType.BOTTLE && dbOrderItem.getBeverage().getId() == beverage.getId()) {
                        isOld = true;
                        int oldQuantity = dbOrderItem.getQuantity();
                        if (oldQuantity < orderItem.getQuantity()) {
                            int newItemsRequired = orderItem.getQuantity() - oldQuantity;
                            if (inStock < newItemsRequired) {
                                return false;
                            }
                        }
                    }
                }
                if (!isOld && (inStock - orderItem.getQuantity() < 0)) {
                    return false;
                }
            }
            if (beverage.getBeverageType() == BeverageType.CRATE) {
                Crate crate = this.db.crates.get(beverage.getId() - 1);
                int inStock = crate.getInStock();
                boolean isOld = false;
                for (final OrderItem dbOrderItem : dbOrder.getOrderItems()) {
                    isOld = true;
                    if (dbOrderItem.getBeverage().getBeverageType() == BeverageType.CRATE && dbOrderItem.getBeverage().getId() == beverage.getId()) {
                        int oldQuantity = dbOrderItem.getQuantity();
                        if (oldQuantity < orderItem.getQuantity()) {
                            int newItemsRequired = orderItem.getQuantity() - oldQuantity;
                            if (inStock < newItemsRequired) {
                                return false;
                            }
                        }
                    }
                }
                if (!isOld && (inStock - orderItem.getQuantity() < 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean updateOrder(Order order, int orderId) {
        Order dbOrder = this.db.orders.stream().filter(c -> c.getOrderId() == orderId).findFirst().orElse(null);
        if (isOrderUpdateValid(order, dbOrder)) {
            for (final OrderItem orderItem : order.getOrderItems()) {
                Beverage beverage = orderItem.getBeverage();
                if (beverage.getBeverageType() == BeverageType.BOTTLE) {
                    Bottle bottle = this.db.bottles.get(beverage.getId() - 1);
                    int inStock = bottle.getInStock();
                    boolean isOld = false;
                    for (final OrderItem dbOrderItem : dbOrder.getOrderItems()) {
                        if (dbOrderItem.getBeverage().getBeverageType() == BeverageType.BOTTLE && dbOrderItem.getBeverage().getId() == beverage.getId()) {
                            isOld = true;
                            int oldQuantity = dbOrderItem.getQuantity();
                            if (oldQuantity < orderItem.getQuantity()) {
                                int newItemsRequired = orderItem.getQuantity() - oldQuantity;
                                bottle.setInStock(inStock - newItemsRequired);
                            } else {
                                int itemsRemoved = oldQuantity - orderItem.getQuantity();
                                bottle.setInStock(inStock + itemsRemoved);
                            }
                        }
                    }
                    if (!isOld) {
                        bottle.setInStock(inStock - orderItem.getQuantity());
                    }
                }
                if (beverage.getBeverageType() == BeverageType.CRATE) {
                    Crate crate = this.db.crates.get(beverage.getId() - 1);
                    int inStock = crate.getInStock();
                    boolean isOld = false;
                    for (final OrderItem dbOrderItem : dbOrder.getOrderItems()) {
                        if (dbOrderItem.getBeverage().getBeverageType() == BeverageType.CRATE && dbOrderItem.getBeverage().getId() == beverage.getId()) {
                            isOld = true;
                            int oldQuantity = dbOrderItem.getQuantity();
                            if (oldQuantity < orderItem.getQuantity()) {
                                int newItemsRequired = orderItem.getQuantity() - oldQuantity;
                                crate.setInStock(inStock - newItemsRequired);
                            } else {
                                int itemsRemoved = oldQuantity - orderItem.getQuantity();
                                crate.setInStock(inStock + itemsRemoved);
                            }
                        }
                    }
                    if (!isOld) {
                        crate.setInStock(inStock - orderItem.getQuantity());
                    }
                }
            }
//            Order dbOrder = this.db.orders.stream().filter(c -> c.getOrderId() == orderId).findFirst().orElse(null);
            if (order == null || dbOrder == null) {
                return false;
            }
            if (dbOrder.getStatus() == OrderStatus.PROCESSED || dbOrder.getStatus() == OrderStatus.CANCELLED) {
                return false;
            }
            dbOrder.setPrice(order.getPrice());
            dbOrder.setOrderItems(order.getOrderItems());
            return true;
        } return false;
    }
    //PUT
    /**
     * Update an existing order status.
     *@author: Deepika Arneja
     * @param orderId
     * @param status
     * @return True or False
     */

    public boolean updateStatus(final int orderId, OrderStatus status){
        Order dbOrder = this.db.orders.stream().filter(c -> c.getOrderId() == orderId).findFirst().orElse(null);
        if(dbOrder == null) return false;
        if(dbOrder.getStatus() == OrderStatus.PROCESSED) return false;
        if(dbOrder.getStatus() == OrderStatus.CANCELLED) return false;
        if(status == OrderStatus.SUBMITTED) return false;
        dbOrder.setStatus(status);
        return true;
    }
}