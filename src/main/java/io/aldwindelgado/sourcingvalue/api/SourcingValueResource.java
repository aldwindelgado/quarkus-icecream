package io.aldwindelgado.sourcingvalue.api;

import io.aldwindelgado.sourcingvalue.service.SourcingValueService;
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
@Path("/sourcing-values")
@Produces(MediaType.APPLICATION_JSON)
public class SourcingValueResource {

    private static final Logger log = Logger.getLogger(SourcingValueResource.class);

    private final SourcingValueService service;

    public SourcingValueResource(SourcingValueService service) {
        this.service = service;
    }

    @GET
    public Response getAll() {
        log.info("REST request to retrieve all sourcing values");
        return Response.ok(service.getAll()).build();
    }

    @GET
    @Path("/{name}")
    public Response getByName(@PathParam("name") String name) {
        log.infov("REST request to retrieve sourcing value by name: {0}", name);
        return Response.ok(service.getByName(name)).build();
    }

}
