package io.aldwindelgado.product.api;

import io.aldwindelgado.product.service.ProductService;
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
}
