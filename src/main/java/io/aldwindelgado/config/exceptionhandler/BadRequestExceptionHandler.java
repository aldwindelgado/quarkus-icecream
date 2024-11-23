package io.aldwindelgado.config.exceptionhandler;

import io.aldwindelgado.config.exceptionhandler.GenericExceptionHandler.ApiErrorDto;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * @author Aldwin Delgado
 */
@Provider
public class BadRequestExceptionHandler implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException exception) {
        return Response.status(Status.BAD_REQUEST)
            .type(GenericExceptionHandler.PROBLEM_JSON_HEADER)
            .entity(new ApiErrorDto(exception.getMessage())).build();
    }
}
