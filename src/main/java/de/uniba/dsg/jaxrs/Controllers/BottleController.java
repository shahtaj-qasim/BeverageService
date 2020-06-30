package de.uniba.dsg.jaxrs.Controllers;

import de.uniba.dsg.jaxrs.Services.BottleService;
import de.uniba.dsg.jaxrs.model.DTO.BottleDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.logic.Bottle;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;

@Path("Bottle")
public class BottleController {

    @Path("/getBottles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBottles(@DefaultValue("0") @QueryParam("page") int page, @DefaultValue("1000") @QueryParam("pageSize") int pageSize,@DefaultValue("0") @QueryParam("minPrice")double minPrice,@DefaultValue("1000") @QueryParam("maxPrice") double maxPrice,@QueryParam("query") String query) throws IOException {

        if (page < 0 || pageSize <0 || minPrice <0 || maxPrice <0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Parameters value can not be negative")).build();
        }

        String resp = BottleService.instance.getBottles(page,pageSize,minPrice,maxPrice,query);

        return Response
                .status(Response.Status.OK)
                .entity(resp)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    /*
    @author: Deepika Arneja
    @description: Get beverageType Bottle by Id
     */
    @Path("/{bottleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBottles(@PathParam("bottleId") final int id) {
        final Bottle bottle = BottleService.instance.getBottleById(id);

        if (bottle == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "BottleId not found")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(new BottleDTO(bottle))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @Path("/addBottle")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBottle(BottleDTO newBottle) {
        if (newBottle == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }
        Bottle bottles = newBottle.MapToBottle();
        Bottle bottle = BottleService.instance.submitBottle(bottles);
        return Response
                .status(Response.Status.OK)
                .entity(new BottleDTO(bottle))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Path("/updateBottle/{bottleId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBottle(BottleDTO newBottle , @PathParam("bottleId") final int id) {
        if (newBottle == null ) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        Bottle bottles = newBottle.MapToBottle();
        Bottle updatedBottle = BottleService.instance.updateBottle(bottles, id);

        if (updatedBottle == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Bottle not available.")).build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(new BottleDTO(updatedBottle))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}