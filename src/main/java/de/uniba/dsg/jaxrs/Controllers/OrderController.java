package de.uniba.dsg.jaxrs.Controllers;

import de.uniba.dsg.jaxrs.Services.OrderService;
import de.uniba.dsg.jaxrs.model.DTO.OrderDTO;
import de.uniba.dsg.jaxrs.model.logic.Order;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.logic.OrderStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.logging.Logger;

@Path("Order")
public class OrderController {

    private static final Logger logger =Logger.getLogger("OrderController");
    /*
      @Author: Shavez Hameed
      @Description: Get request to fetch order by order ID
    */
    @GET
    @Path("{orderId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getOrder(@PathParam("orderId") final int orderId) {
        logger.info( "Get order with Id " + orderId);
        final Order order = OrderService.instance.getOrderById(orderId);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "OrderId not found")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(new OrderDTO(order))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    /*
    @Author: Shavez Hameed
    @Description: Post request used for submitting a new order
    */
    @POST
    @Path("createOrder")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
        public Response createOrder(OrderDTO newOrder) {
        logger.info("Create Order " + newOrder);
        if (newOrder == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }
        Order orders = newOrder.MapToOrder();
        Order order = OrderService.instance.submitOrder(orders);
        if (order == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Some of items are out of stock")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(new OrderDTO(order))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    /*
    @author: Deepika Arneja
     */
    @PUT
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrder(@PathParam("orderId") final int orderId, OrderDTO updateOrder) {
        logger.info("Update Order " + updateOrder);
        if (updateOrder == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Order details cannot be null")).build();
        }
        Order orders = updateOrder.MapToOrder();
        boolean isUpdated = OrderService.instance.updateOrder(orders, orderId);
        if (isUpdated) {
            return Response.ok()
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Order cannot be updated")).build();
    }
    /*
    @author: Deepika Arneja
     */

    @PUT
    @Path("/{orderId}/{newStatus}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStatus(@PathParam("orderId") final int orderId,
                                 @PathParam("newStatus") OrderStatus newStatus) {
        logger.info("Update Status " + newStatus);
        if (newStatus == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Status cannot be null")).build();
        }
        boolean isUpdated = OrderService.instance.updateStatus(orderId, newStatus);
        if (isUpdated) {
            return Response.ok()
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "The status cannot be updated")).build();
    }

}