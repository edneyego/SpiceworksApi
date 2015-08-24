package org.pcarrasco.spiceworksapi.webresource;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.pcarrasco.spiceworksapi.models.Ticket;
import org.pcarrasco.spiceworksapi.service.ServiceResponse;
import org.pcarrasco.spiceworksapi.service.SpiceworksApiService;
import org.pcarrasco.spiceworksapi.utils.SpiceworksApiAbstractModule;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Ticket resource - exposes /tickets path
 */
@Path("/tickets")
public class TicketResource {

    private final SpiceworksApiService service;

    public TicketResource() {
        Injector injector = Guice.createInjector(new SpiceworksApiAbstractModule());
        service = injector.getInstance(SpiceworksApiService.class);
    }

    @GET
    @Path("/{ticketId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Ticket getTicket(@PathParam("ticketId") String ticketId) {
        ServiceResponse<Ticket> response = service.getTicket(Integer.parseInt(ticketId));
        handleError(response);

        return response.getData();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Ticket postTicket(Ticket ticket) {
        ServiceResponse<Ticket> response = service.createTicket(ticket);
        handleError(response);

        return response.getData();
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putTicket(Ticket ticket) {
        ServiceResponse<Ticket> response = service.updateTicket(ticket);
        handleError(response);
    }

    @DELETE
    @Path("/delete/{ticketId}")
    public void deleteTicket(@PathParam("ticketId") int ticketId) {
        ServiceResponse<Ticket> response = service.deleteTicket(ticketId);
        handleError(response);
    }

    private void handleError(ServiceResponse<Ticket> response) {
        if (response.getHasValidationError())
            throw new ServiceValidationFailedExceprion(response.getValidationMessage());
        else if (response.getHasException())
            throw new ServiceInternalException(response.getExceptionMessagexceptionMessage());
    }
}
