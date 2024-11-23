package io.aldwindelgado.product.api;

import io.aldwindelgado.product.api.exchange.ProductRequestDto;
import io.aldwindelgado.product.service.ProductService;
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
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private static final Logger log = Logger.getLogger(ProductResource.class);

    private final ProductService service;

    public ProductResource(ProductService service) {
        this.service = service;
    }

    @GET
    public Response getAll() {
        log.info("REST request to retrieve all products");
        return Response.ok(service.getAll()).build();
    }

    @GET
    @Path("/{name}")
    public Response getByName(@PathParam("name") String name) {
        log.infov("REST request to retrieve product by name: {0}", name);
        return Response.ok(service.getByName(name)).build();
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(ProductRequestDto requestDto) {
        log.infov("REST request to create a new product: {0}", requestDto);
        service.create(requestDto);
        return Response.created(buildLocationPath(requestDto.getName())).build();
    }

    private static URI buildLocationPath(String value) {
        final var uriBuilder = UriBuilder.fromPath("/products/" + value);
        return URI.create(uriBuilder.toTemplate());
    }
}
