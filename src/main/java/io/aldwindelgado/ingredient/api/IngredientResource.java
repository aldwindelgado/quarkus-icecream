package io.aldwindelgado.ingredient.api;

import io.aldwindelgado.ingredient.service.IngredientService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;

/**
 * @author Aldwin Delgado
 */
@Path("/ingredients")
@Produces(MediaType.APPLICATION_JSON)
public class IngredientResource {

    private static final Logger log = Logger.getLogger(IngredientResource.class);

    private final IngredientService service;

    public IngredientResource(IngredientService service) {
        this.service = service;
    }

    @GET
    public Response getAll() {
        log.info("REST request to retrieve all ingredients");
        return Response.ok(service.getAll()).build();
    }

    @GET
    @Path("/{name}")
    public Response getByName(@PathParam("name") String name) {
        log.infov("REST request to retrieve ingredient by name: {0}", name);
        return Response.ok(service.getByName(name)).build();
    }

}
