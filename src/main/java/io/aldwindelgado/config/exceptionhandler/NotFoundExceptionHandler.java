package io.aldwindelgado.config.exceptionhandler;

import io.aldwindelgado.config.exceptionhandler.GenericExceptionHandler.ApiErrorDto;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Aldwin Delgado
 */
@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Status.NOT_FOUND)
            .type(GenericExceptionHandler.PROBLEM_JSON_HEADER)
            .entity(new ApiErrorDto(exception.getMessage())).build();
    }
}
