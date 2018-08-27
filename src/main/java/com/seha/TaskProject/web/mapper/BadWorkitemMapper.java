package com.seha.TaskProject.web.mapper;

import com.seha.TaskProject.service.exceptions.BadWorkitemException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;

@Provider
public class BadWorkitemMapper implements ExceptionMapper<BadWorkitemException> {

    @Override
    public Response toResponse(BadWorkitemException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Collections.singletonMap("error", e.getMessage()))
                .build();
    }
}