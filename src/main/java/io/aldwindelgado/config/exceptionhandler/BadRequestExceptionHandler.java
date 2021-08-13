package io.aldwindelgado.config.exceptionhandler;

import io.aldwindelgado.config.exceptionhandler.GenericExceptionHandler.ApiErrorDto;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

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
