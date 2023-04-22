package org.pl.gateway.module.mappers;

import jakarta.json.Json;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<ProcessingException> {
    @Override
    public Response toResponse(ProcessingException exception) {
        return Response.status(400).entity(Json.createObjectBuilder()
                .add("error", "One of the arguments is in invalid format")
                .build()).type(MediaType.APPLICATION_JSON).build();
    }
}
