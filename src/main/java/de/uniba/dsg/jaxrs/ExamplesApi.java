package de.uniba.dsg.jaxrs;


import de.uniba.dsg.jaxrs.Controllers.BottleController;
import de.uniba.dsg.jaxrs.Controllers.CrateController;
import de.uniba.dsg.jaxrs.Controllers.OrderController;
import de.uniba.dsg.jaxrs.resources.SwaggerUI;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExamplesApi extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();
        resources.add(OrderController.class);
        resources.add(BottleController.class);
        resources.add(CrateController.class);
        resources.add(SwaggerUI.class);
        return resources;
//    public String sayPlainTextHello() {
//        return "Hello Jersey Server";

    }
}
