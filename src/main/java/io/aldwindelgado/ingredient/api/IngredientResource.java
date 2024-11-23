package io.aldwindelgado.ingredient.api;

import io.aldwindelgado.ingredient.api.exchange.IngredientRequestDto;
import io.aldwindelgado.ingredient.service.IngredientService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import java.net.URI;
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

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(IngredientRequestDto requestDto) {
        log.infov("REST request to create a new ingredient: {0}", requestDto);
        service.create(requestDto);
        return Response.created(buildLocationPath(requestDto.getName())).build();
    }

    private static URI buildLocationPath(String value) {
        final var uriBuilder = UriBuilder.fromPath("/ingredients/" + value);
        return URI.create(uriBuilder.toTemplate());
    }
}
