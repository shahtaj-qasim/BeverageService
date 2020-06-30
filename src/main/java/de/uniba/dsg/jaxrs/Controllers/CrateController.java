package de.uniba.dsg.jaxrs.Controllers;

import de.uniba.dsg.jaxrs.Services.CrateService;
import de.uniba.dsg.jaxrs.model.DTO.CrateDTO;
import de.uniba.dsg.jaxrs.model.error.ErrorMessage;
import de.uniba.dsg.jaxrs.model.error.ErrorType;
import de.uniba.dsg.jaxrs.model.logic.Crate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("Crate")
public class CrateController {

    @Path("/getCrates")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrates(@DefaultValue("0") @QueryParam("page") int page, @DefaultValue("10") @QueryParam("pageSize") int pageSize,@DefaultValue("0") @QueryParam("minPrice")double minPrice,@DefaultValue("1000") @QueryParam("maxPrice") double maxPrice,@QueryParam("query") String query) throws IOException {

        if (page < 0 || pageSize <0 || minPrice <0 || maxPrice <0) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Parameters value can not be negative")).build();
        }

        String resp = CrateService.instance.getCrates(page,pageSize,minPrice,maxPrice,query);

        return Response
                .status(Response.Status.OK)
                .entity(resp)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    /*
   @author: Deepika Arneja
   @description: Get beverageType Crate by Id
    */
    @Path("/{crateId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrated(@PathParam("crateId") final int id) {
        final Crate crate = CrateService.instance.getCrateById(id);

        if (crate == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "CrateId not found")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(new CrateDTO(crate))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @Path("/addCrate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCrate(CrateDTO newCrate) {
        if (newCrate == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }
        Crate crates = newCrate.MapToCrate();
        Crate crate = CrateService.instance.submitCrate(crates);
        return Response
                .status(Response.Status.OK)
                .entity(new CrateDTO(crate))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Path("/updateCrate/{crateId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCrate(CrateDTO newCrate, @PathParam("crateId") final int id) {
        if (newCrate == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        Crate crate = newCrate.MapToCrate();
        Crate updateCrate = CrateService.instance.updateCrate(crate, id);

        if (updateCrate == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Crate not available.")).build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(new CrateDTO(updateCrate))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
