package com.seha.TaskProject.web.mapper;

import com.seha.TaskProject.service.exceptions.BadIssueException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;

@Provider
public class BadIssueMapper implements ExceptionMapper<BadIssueException> {

    @Override
    public Response toResponse(BadIssueException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Collections.singletonMap("error", e.getMessage()))
                .build();
    }
}