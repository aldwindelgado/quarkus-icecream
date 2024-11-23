package io.aldwindelgado.config.exceptionhandler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

        private static final long serialVersionUID = -816592890593456103L;

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
