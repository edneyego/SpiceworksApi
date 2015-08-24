package org.pcarrasco.spiceworksapi.webresource;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.pcarrasco.spiceworksapi.models.User;
import org.pcarrasco.spiceworksapi.service.ServiceResponse;
import org.pcarrasco.spiceworksapi.service.SpiceworksApiService;
import org.pcarrasco.spiceworksapi.utils.SpiceworksApiAbstractModule;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * User resource - exposes /users path
 */
@Path("/users")
public class UserResource {

    private final SpiceworksApiService service;

    public UserResource() {
        Injector injector = Guice.createInjector(new SpiceworksApiAbstractModule());
        service = injector.getInstance(SpiceworksApiService.class);
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") String userId) {
        ServiceResponse<User> response = service.getUser(Integer.parseInt(userId));
        handleError(response);

        return response.getData();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User postUser(User user) {
        ServiceResponse<User> response = service.createUser(user);
        handleError(response);

        return response.getData();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putUser(User user) {
        ServiceResponse<User> response = service.updateUser(user);
        handleError(response);
    }

    @DELETE
    @Path("/delete/{userId}")
    public void deleteUser(@PathParam("userId") String userId) {
        ServiceResponse<User> response = service.deleteUser(Integer.parseInt(userId));
        handleError(response);
    }

    private void handleError(ServiceResponse<User> response) {
        if (response.getHasValidationError())
            throw new ServiceValidationFailedExceprion(response.getValidationMessage());
        else if (response.getHasException())
            throw new ServiceInternalException(response.getExceptionMessagexceptionMessage());
    }
}
