package com.stefanini.resources;

import com.stefanini.dto.UserCreationDTO;
import com.stefanini.dto.UserRetrievalDTO;
import com.stefanini.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserResource {
    @Inject
    private UserService userService;
    @GET
    @Path("/users-whose-name-starts-with/{letter}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByNameStartingWith(@PathParam("letter") String letter) {
        List<UserRetrievalDTO> users = userService.findUserByNameStartingWith(letter);
        return users.isEmpty() ? Response.status(Response.Status.NOT_FOUND).build() : Response.status(Response.Status.OK).entity(users).build();
    }
    @GET
    @Path("/users-whose-birth-month-is/{month}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByBirthMonth(@PathParam("month") Integer month) {
        List<UserRetrievalDTO> users = userService.findUsersByBirthMonth(month);
        return users.isEmpty() ? Response.status(Response.Status.NOT_FOUND).build() : Response.status(Response.Status.OK).entity(users).build();
    }
    @GET
    @Path("/email-providers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmailProviders() {
        List<String> emailProviders = userService.getEmailProviders();
        return emailProviders.isEmpty() ? Response.status(Response.Status.NOT_FOUND).build() : Response.status(Response.Status.OK).entity(emailProviders).build();
    }
    @GET
    @Path("/all-users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listUsers() {
        List<UserRetrievalDTO> users = userService.listUsers();
        return users.isEmpty() ? Response.status(Response.Status.NOT_FOUND).build() : Response.status(Response.Status.OK).entity(users).build();
    }

    @GET
    @Path("/user-id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long id) {
        UserRetrievalDTO user = userService.findUserById(id);
        return user != null? Response.status(Response.Status.OK).entity(userService.findUserById(id)).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/user-dto-example")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExampleDTO() {
        return Response.status(Response.Status.OK).entity(UserCreationDTO.userCreationDTOExample).build();
    }

    @POST
    @Path("/create-user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(UserCreationDTO userCreationDTO) {
        Response response;
        if (userService.createUser(userCreationDTO)) {
            response = Response.status(Response.Status.CREATED).build();
        } else {
            response = Response.status(Response.Status.CONFLICT).build();
        }
        return response;
    }

    @DELETE
    @Path("/delete-user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Long id) {
        Response response;
        if (userService.deleteUser(id)) {
            response = Response.status(Response.Status.OK).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }
}
