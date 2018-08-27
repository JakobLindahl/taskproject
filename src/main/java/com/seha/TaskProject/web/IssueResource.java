package com.seha.TaskProject.web;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.seha.TaskProject.data.Issue;
import com.seha.TaskProject.service.IssueService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;

@Component
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("issues")
public final class IssueResource {

    private final IssueService issueService;

    public IssueResource(IssueService issueService) {
        this.issueService = issueService;
    }

    @PUT
    @Path("{id}")
    public Response updateIssue(@PathParam("id") Long id, Issue issue) {
        if (issueService.updateIssue(id, issue)) {
            return Response.status(OK).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @GET
    public Iterable<Issue> getAllTeams() {
        return issueService.getAllIssues();
    }

    @GET
    @Path("pageandsize")
    public List<Issue> getAllIssues(@QueryParam("page") Integer page,
                                    @QueryParam("size") Integer size) {
        return issueService.getAllIssuesByPageAndSize(page, size).getContent();
    }
}