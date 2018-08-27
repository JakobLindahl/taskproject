package com.seha.TaskProject.web;

import org.springframework.stereotype.Component;
import com.seha.TaskProject.data.User;
import com.seha.TaskProject.service.UserService;
import com.seha.TaskProject.web.filter.TokenKey;

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
@Path("users")
public final class UserResource {

    private UserService userService;

    @Context
    private UriInfo uriInfo;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @TokenKey
    public Response createUser(User user) {
        User createdUser = userService.createUser(user);
        return Response.status(CREATED).header("Location",
                uriInfo.getAbsolutePathBuilder().path(createdUser.getUserNumber().toString())).build();
    }

    @PUT
    @Path("{userNumber}")
    public Response updateUserByUserNumber(@PathParam("userNumber") Long userNumber, User user) {
        if (userService.updateUser(userNumber, user)) {
            return Response.status(OK).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @PUT
    @Path("{userNumber}/inactivate")
    public Response inactivateUser(@PathParam("userNumber") Long userNumber) {
        if (userService.inactivateUser(userNumber)) {
            return Response.status(OK).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @GET
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Path("{userNumber}")
    public Response getUserByUserNumber(@PathParam("userNumber") Long userNumber) {
        return userService.getUserByUserNumber(userNumber)
                .map(Response::ok)
                .orElse(Response.status(NOT_FOUND))
                .build();
    }

    @GET
    @Path("team/{teamName}")
    public List<User> getAllUsersFromSpecificTeam(@PathParam("teamName") String teamName) {
        return userService.getAllUsersInTeamByTeamName(teamName);
    }

    @GET
    @Path("by")
    public List<User> getUsersByFirstNameAndLastNameAndUserName
            (@QueryParam("firstName") String firstName,
             @QueryParam("lastName") String lastName,
             @QueryParam("userName") String userName) {
        return userService.findUsersByFirstNameAndLastNameAndUserName(firstName, lastName, userName);
    }

    @GET
    @Path("pageandsize")
    public List<User> getAllUsersByPageAndSize(@QueryParam("page") Integer page,
                                               @QueryParam("size") Integer size) {
        return userService.getAllUsersByPageAndSize(page, size).getContent();
    }
}