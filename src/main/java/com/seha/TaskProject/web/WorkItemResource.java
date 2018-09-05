package com.seha.TaskProject.web;

import com.seha.TaskProject.data.Issue;
import com.seha.TaskProject.data.WorkItem;
import com.seha.TaskProject.data.workitemenum.Status;
import com.seha.TaskProject.service.WorkItemService;
import com.seha.TaskProject.web.filter.TokenKey;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;

@Component
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("workitems")
public final class WorkItemResource {

    private final WorkItemService workItemService;

    @Context
    private UriInfo uriInfo;

    public WorkItemResource(WorkItemService workItemService) {
        this.workItemService = workItemService;
    }

    @POST
    @TokenKey
    public Response createWorkItem(WorkItem workItem) {
        WorkItem createdWorkItem = workItemService.createWorkItem(workItem);
        return Response.status(CREATED).header("Location",
                uriInfo.getAbsolutePathBuilder().path(createdWorkItem.getId().toString())).build();
    }

    @POST
    @TokenKey
    @Path("{id}/issues")
    public Response addIssueToWorkItem(@PathParam("id") Long id, Issue issue) {
        workItemService.addIssueToWorkItem(id, issue);
        return Response.status(CREATED).build();
    }

    @PUT
    @Path("{id}/helpers/{userId}")
    public Response addWorkItemByHelperId(@PathParam("id") Long workItemId,
                                        @PathParam("userId") Long userId) {
        workItemService.addWorkItemByHelperId(workItemId, userId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("{id}/users/{userId}")
    public Response addWorkItemByUserId(@PathParam("id") Long workItemId,
                                        @PathParam("userId") Long userId) {
        workItemService.addWorkItemByUserId(workItemId, userId);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}/status")
    public Response changeStatus(@PathParam("id") Long workItemId,
                                 @QueryParam("set") String status) {
        if (workItemService.setStatusOnWorkItem(workItemId, status)) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    public List<WorkItem> getWorkItemsByDateAndStatus(@QueryParam("startDate") String startDate,
                                                      @QueryParam("endDate") String endDate,
                                                      @QueryParam("status")String status) {
        return workItemService.getWorkItemsByDate(startDate, endDate, status);
    }


    @GET
    @Path("all")
    public List<WorkItem> getAllWorkItems() {
        return workItemService.getAllWorkItems();

    }

    @GET
    @Path("{id}")
    public Response getWorkItem(@PathParam("id") Long id) {
        return workItemService.getWorkItem(id)
                .map(Response::ok)
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    @GET
    @Path("team/{id}")
    public List<WorkItem> getAllWorkItemsForTeam(@PathParam("id") Long teamId) {
        return workItemService.getAllWorkItemsByTeamId(teamId);
    }

    @GET
    @Path("user/{id}")
    public List<WorkItem> getAllWorkItemsForUser(@PathParam("id") Long userId) {
        return workItemService.getAllWorkItemsByUserId(userId);
    }

    @GET
    @Path("description/{description}")
    public List<WorkItem> getWorkItemsByDescription(@PathParam("description") String value) {
        return workItemService.getAllWorkItemsByDescription(value);
    }

    @GET
    @Path("issues")
    public List<WorkItem> getAllWorkItemsWithIssues() {
        return workItemService.getAllWorkItemsWithIssues();
    }

    @GET
    @Path("status/{statusValue}")
    public List<WorkItem> getAllWorkItemsByStatus(@PathParam("statusValue") Status statValue) {
        return workItemService.getAllWorkItemsByStatus(statValue);
    }

    @GET
    @Path("pageandsize")
    public List<WorkItem> getAllWorkItemsByPageAndSize(@QueryParam("page") Integer page,
                                                       @QueryParam("size") Integer size) {
        return workItemService.getAllWorkItemsByPageAndSize(page, size).getContent();
    }

    @DELETE
    @Path("{id}")
    public Response deleteWorkItem(@PathParam("id") Long id) {
        workItemService.deleteWorkItem(id);
        if (workItemService.deleteWorkItem(id)) {
            return Response.status(NO_CONTENT).build();
        }
        return Response.status(NOT_FOUND).build();
    }
}