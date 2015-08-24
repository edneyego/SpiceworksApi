package org.pcarrasco.spiceworksapi.webresource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Exception for entity validation errors
 */
public class ServiceValidationFailedExceprion extends WebApplicationException {
    public ServiceValidationFailedExceprion(String message) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(message).type(MediaType.TEXT_PLAIN).build());
    }
}