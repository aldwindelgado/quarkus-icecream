package io.aldwindelgado.sourcingvalue.api;

import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueRequestDto;
import io.aldwindelgado.sourcingvalue.service.SourcingValueService;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
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
