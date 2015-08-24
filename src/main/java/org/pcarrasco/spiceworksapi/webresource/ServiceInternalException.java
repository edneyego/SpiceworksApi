package org.pcarrasco.spiceworksapi.webresource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Exception for service errors
 */
public class ServiceInternalException extends WebApplicationException {
    public ServiceInternalException(String message) {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}