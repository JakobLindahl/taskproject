package com.seha.TaskProject.web.filter;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static javax.ws.rs.Priorities.AUTHENTICATION;
import static java.util.Collections.singletonMap;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static javax.ws.rs.core.Response.status;

@TokenKey
@Provider
@Priority(AUTHENTICATION)
public class TokenKeyFilter implements ContainerRequestFilter {
    private static List<String> keys = Arrays.asList("serendipity", "chaos", "padawan");

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        final String key = containerRequestContext.getHeaderString("password");
        if(!keys.stream().anyMatch(k -> k.equals(key))) {
            containerRequestContext.abortWith(status(UNAUTHORIZED)
                    .entity(singletonMap("invalid", "password"))
                    .build());
        }
    }
}

