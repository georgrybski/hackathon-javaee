package com.stefanini.resources;

import com.stefanini.dto.UserCreationDTO;
import com.stefanini.dto.UserRetrievalDTO;
import com.stefanini.service.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UserResource {
    @Inject
    private UserService userService;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListOfUsersThatStartWithLetter(@QueryParam("firstLetter") String letter) {
        List<UserRetrievalDTO> users = userService.findUserByNameStartingWith(letter);
        return Response.status(Response.Status.OK).entity(users).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListOfUsersBornInMonth(@QueryParam("month") Integer month) {
        List<UserRetrievalDTO> users = userService.findUsersByBirthMonth(month);
        return Response.status(Response.Status.OK).entity(users).build();
    }
    @GET
    @Path("/email-providers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmailProviderList() {
        List<String> emailProviders = userService.getEmailProviders();
        return Response.status(Response.Status.OK).entity(emailProviders).build();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersList() {
        List<UserRetrievalDTO> users = userService.listUsers();
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long id) {
        UserRetrievalDTO user = userService.findUserById(id);
        return user != null? Response.status(Response.Status.OK).entity(userService.findUserById(id)).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/dto-example")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExampleDTO() {
        return Response.status(Response.Status.OK).entity(UserCreationDTO.userCreationDTOExample).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid UserCreationDTO userCreationDTO) {
        Response response;
        if (userService.createUser(userCreationDTO)) {
            response = Response.status(Response.Status.CREATED).build();
        } else {
            response = Response.status(Response.Status.CONFLICT).build();
        }
        return response;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, UserCreationDTO userCreationDTO) {
        Response response;
        if (userService.updateUser(id, userCreationDTO)) {
            response = Response.status(Response.Status.CREATED).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return response;
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response patchUser(@PathParam("id") Long id, Map<String, Object> patchData) {
        Response response;
        if (userService.patchUser(id, patchData)) {
            response = Response.status(Response.Status.CREATED).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return response;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Long id) {
        Response response;
        if (userService.deleteUser(id)) {
            response = Response.status(Response.Status.OK).build();
        } else {
            response = Response.status(Response.Status.NO_CONTENT).build();
        }
        return response;
    }
}
