package io.aldwindelgado.config.exceptionhandler;

import io.aldwindelgado.config.exceptionhandler.GenericExceptionHandler.ApiErrorDto;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

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
