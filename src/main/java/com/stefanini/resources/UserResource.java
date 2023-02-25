package com.stefanini.resources;

import com.stefanini.dto.LoginRequest;
import com.stefanini.dto.UserCreationDTO;
import com.stefanini.dto.UserRetrievalDTO;
import com.stefanini.service.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    private UserService userService;
    @GET
    public Response getUsersList() {
        return Response.status(Response.Status.OK).entity(userService.listUsers()).build();
    }
    @GET
    @Path("/name-starting-with/")
    public Response getListOfUsersThatStartWithLetter(@QueryParam("letter") String letter) {
        List<UserRetrievalDTO> users = userService.findUserByNameStartingWith(letter);
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @GET
    @Path("/login-contains/")
    public Response getListOfUsersThatLoginContains(@QueryParam("string") String string) {
        List<UserRetrievalDTO> users = userService.findUserByLoginContaining(string);
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @GET
    @Path("/name-contains/")
    public Response getListOfUsersThatNameContains(@QueryParam("string") String string) {
        List<UserRetrievalDTO> users = userService.findUserByNameContaining(string);
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @GET
    @Path("/email-contains/")
    public Response getListOfUsersThatEmailContains(@QueryParam("string") String string) {
        List<UserRetrievalDTO> users = userService.findUserByEmailContaining(string);
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @GET
    @Path("/born-in/")
    public Response getListOfUsersBornInMonth(@QueryParam("month") Integer month) {
        List<UserRetrievalDTO> users = userService.findUsersByBirthMonth(month);
        return Response.status(Response.Status.OK).entity(users).build();
    }
    @GET
    @Path("/email-providers")
    public Response getEmailProviderList() {
        List<String> emailProviders = userService.getEmailProviders();
        return Response.status(Response.Status.OK).entity(emailProviders).build();
    }

    @GET
    @Path("/email-providers-with-count")
    public Response getEmailProviderListWithCount() {
        LinkedHashMap<String, Long> emailProviders = userService.getEmailProvidersWithCount();
        return Response.status(Response.Status.OK).entity(emailProviders).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        return Response.status(Response.Status.OK).entity(userService.findUserById(id)).build();

    }

    @GET
    @Path("/username/{login}")
    public Response login(@PathParam("login") String login) {
        return Response.status(Response.Status.OK).entity(userService.findUserByLogin(login)).build();
    }

    @GET
    @Path("/email/{email}")
    public Response email(@PathParam("email") String email) {
        return Response.status(Response.Status.OK).entity(userService.findUserByEmail(email)).build();
    }

    @GET
    @Path("/dto-example")
    public Response getExampleDTO() {
        return Response.status(Response.Status.OK).entity(UserCreationDTO.userCreationDTOExample).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid UserCreationDTO userCreationDTO) {
        userService.createUser(userCreationDTO);
        return Response.status(Response.Status.CREATED).build();

    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, @Valid UserCreationDTO userCreationDTO) {
        userService.updateUser(id, userCreationDTO);
        return Response.status(Response.Status.OK).build();
    }

    @PATCH
    @Path("/{id}")
    public Response patchUser(@PathParam("id") Long id, Map<String, Object> patchData) {
        userService.patchUser(id, patchData);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userService.deleteUser(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest loginRequest) {
        return userService.validateLoginRequest(loginRequest) ?
                Response.status(Response.Status.OK).build() :
                Response.status(Response.Status.UNAUTHORIZED).build();

    }

}
