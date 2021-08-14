package io.aldwindelgado.product.api;

import io.aldwindelgado.product.api.exchange.ProductRequestDto;
import io.aldwindelgado.product.service.ProductService;
import java.net.URI;
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
