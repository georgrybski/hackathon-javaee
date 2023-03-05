package com.stefanini.resources;

import com.stefanini.dto.LoginRequest;
import com.stefanini.dto.UserCreationDTO;
import com.stefanini.service.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    private UserService userService;

    @GET
    public Response getUsersList(@QueryParam("id") Long id,
                                 @QueryParam("name") String name,
                                 @QueryParam("login") String login,
                                 @QueryParam("email") String email,
                                 @QueryParam("birthMonth") Integer month,
                                 @QueryParam("emailProvider") String emailProvider) {

        return Response.status(Response.Status.OK).entity(userService.listUsers(id, name, login, email, month, emailProvider)).build();
    }

    @GET
    @Path("/login-available")
    public Response isLoginAvailable(@QueryParam("login") String login) {
        return Response.status(Response.Status.OK).entity(userService.isLoginAvailable(login)).build();
    }


    @GET
    @Path("/email-providers")
    public Response getEmailProviderList() {
        List<String> emailProviders = userService.getEmailProviders();
        return Response.status(Response.Status.OK).entity(emailProviders).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        return Response.status(Response.Status.OK).entity(userService.findUserById(id)).build();

    }

    @GET
    @Path("/dto-example")
    public Response getExampleDTO() {
        return Response.status(Response.Status.OK).entity(UserCreationDTO.userCreationDTOExample).build();
    }

    @POST
    public Response createUser(@Valid UserCreationDTO userCreationDTO) {
        userService.createUser(userCreationDTO);
        return Response.status(Response.Status.CREATED).build();

    }

    @POST
    @Path("/batch/delete")
    public Response deleteUsersBatch(List<Long> ids) {
        userService.deleteUsersBatch(ids);
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/login")
    public Response login(@Valid LoginRequest loginRequest) {
        return userService.validateLoginRequest(loginRequest) ? Response.status(Response.Status.OK).build() : Response.status(Response.Status.UNAUTHORIZED).build();

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
}
