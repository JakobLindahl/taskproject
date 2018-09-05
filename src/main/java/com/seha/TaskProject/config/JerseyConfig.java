package com.seha.TaskProject.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.seha.TaskProject.service.exceptions.*;
import com.seha.TaskProject.web.IssueResource;
import com.seha.TaskProject.web.TeamResource;
import com.seha.TaskProject.web.UserResource;
import com.seha.TaskProject.web.WorkItemResource;
import com.seha.TaskProject.web.filter.TokenKeyFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(TokenKeyFilter.class);
        register(IssueResource.class);
        register(TeamResource.class);
        register(UserResource.class);
        register(WorkItemResource.class);
        register(BadWorkitemException.class);
        register(BadTeamException.class);
        register(BadUserException.class);
        register(BadIssueException.class);
        register(ToManyTeamsException.class);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new ParameterNamesModule());
    }
}