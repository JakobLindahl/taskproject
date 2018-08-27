package com.seha.TaskProject.web.mapper;

import com.seha.TaskProject.service.exceptions.BadUserException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;

@Provider
public class BadUserMapper implements ExceptionMapper<BadUserException> {

    @Override
    public Response toResponse(BadUserException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Collections.singletonMap("error", e.getMessage()))
                .build();
    }
}