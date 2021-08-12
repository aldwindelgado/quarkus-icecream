package io.aldwindelgado.config.exceptionhandling;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Aldwin Delgado
 */
@Provider
public class GenericExceptionHandler implements ExceptionMapper<Throwable> {

    static final String PROBLEM_JSON_HEADER = "application/problem+json";

    @Override
    public Response toResponse(Throwable exception) {
        return Response.status(Status.INTERNAL_SERVER_ERROR)
            .type(PROBLEM_JSON_HEADER)
            .entity(new ApiErrorDto(exception.getMessage())).build();
    }

    static class ApiErrorDto implements Serializable {

        private final String timestamp;

        private final String message;

        ApiErrorDto(String message) {
            final var dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
            timestamp = ZonedDateTime.now(ZoneId.of("UTC")).format(dateFormat);
            this.message = message;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getMessage() {
            return message;
        }
    }
}
