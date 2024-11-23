package io.aldwindelgado.sourcingvalue.api;

import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueRequestDto;
import io.aldwindelgado.sourcingvalue.service.SourcingValueService;
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

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(SourcingValueRequestDto requestDto) {
        log.infov("REST request to create a new sourcing value: {0}", requestDto);
        service.create(requestDto);
        return Response.created(buildLocationPath(requestDto.getName())).build();
    }

    private static URI buildLocationPath(String value) {
        final var uriBuilder = UriBuilder.fromPath("/sourcing-values/" + value);
        return URI.create(uriBuilder.toTemplate());
    }
}
