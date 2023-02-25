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
import java.util.Optional;

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
    public Response getListOfUsersThatStartWithLetter(@QueryParam("firstLetter") String letter) {
        List<UserRetrievalDTO> users = userService.findUserByNameStartingWith(letter);
        return Response.status(Response.Status.OK).entity(users).build();
    }
    @GET
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
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        Optional<UserRetrievalDTO> user = userService.findUserById(id);
        Response response;
        if (user.isPresent()) {
            response = Response.status(Response.Status.OK).entity(user.get()).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

    @GET
    @Path("/dto-example")
    public Response getExampleDTO() {
        return Response.status(Response.Status.OK).entity(UserCreationDTO.userCreationDTOExample).build();
    }

    @POST
    public Response createUser(@Valid UserCreationDTO userCreationDTO) {
        Response response;
        if (userService.createUser(userCreationDTO)) {
            response = Response.status(Response.Status.CREATED).build();
        } else {
            response = Response.status(Response.Status.CONFLICT).build();
        }
        return response;
    }

    @POST
    @Path("/batch/delete")
    public Response deleteUsersBatch(List<Long> ids) {
        userService.deleteUsersBatch(ids);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, UserCreationDTO userCreationDTO) {
        Response response;
        if (userService.updateUser(id, userCreationDTO)) {
            response = Response.status(Response.Status.OK).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return response;
    }

    @PATCH
    @Path("/{id}")
    public Response patchUser(@PathParam("id") Long id, Map<String, Object> patchData) {
        Response response;
        if (userService.patchUser(id, patchData)) {
            response = Response.status(Response.Status.OK).build();
        } else {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        }
        return response;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        Response response;
        if (userService.deleteUser(id)) {
            response = Response.status(Response.Status.NO_CONTENT).build();
        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }
}
