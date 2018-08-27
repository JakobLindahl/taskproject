package com.seha.TaskProject.web;

import org.springframework.stereotype.Component;
import com.seha.TaskProject.data.Team;
import com.seha.TaskProject.service.TeamService;
import com.seha.TaskProject.web.filter.TokenKey;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.*;

@Component
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("teams")
public final class TeamResource {

    private final TeamService teamService;

    @Context
    private UriInfo uriInfo;

    public TeamResource(TeamService teamService) {
        this.teamService = teamService;
    }

    @POST
    @TokenKey
    public Response createTeam(Team team) {
        teamService.createTeam(team);
        return Response.status(CREATED).header("Location",
                uriInfo.getAbsolutePathBuilder().path(team.getName())).build();
    }
    @PUT
    @Path("{id}")
    public Response updateTeamById(@PathParam("id") Long id, Team team) {
        if (teamService.updateTeam(id, team)) {
            return Response.status(OK).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @PUT
    @Path("{id}/inactivate")
    public Response inactivateTeam(@PathParam("id") Long id) {
        if (teamService.inactivateTeam(id)) {
            return Response.status(OK).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @PUT
    @Path("{id}/users/{userNumber}")
    public Response setUserToTeam(@PathParam("id") Long id,
                                  @PathParam("userNumber") Long userNumber) {
        teamService.setUserToTeam(id, userNumber);
        return Response.status(OK).build();
    }

    @GET
    public Iterable<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

}