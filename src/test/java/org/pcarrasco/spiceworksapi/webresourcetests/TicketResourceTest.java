package org.pcarrasco.spiceworksapi.webresourcetests;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.json.JSONConfiguration;
import org.eclipse.persistence.exceptions.BeanValidationException;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pcarrasco.spiceworksapi.models.Ticket;
import org.pcarrasco.spiceworksapi.models.User;
import org.pcarrasco.spiceworksapi.webresource.SpiceworksApiMainServer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Date;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;

/**
 * Test Ticket Jersey resource
 */
public class TicketResourceTest {

    private HttpServer server;
    private Client client;

    @Before
    public void setUp() throws Exception {
        server = SpiceworksApiMainServer.startServer();
        client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testTicketCRUD() {
        // Create a new ticket
        Ticket ticket = new Ticket();
        ticket.setSummary("Test ticket summary");
        ticket.setStatus("open");
        ticket.setPriority(1);
        ticket.setCreatedDate(new Date());

        User creator = new User();
        creator.setId(1);
        ticket.setCreator(creator);

        WebTarget target = client.target(SpiceworksApiMainServer.BASE_URI).path("tickets/create");
        ticket = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(ticket, MediaType.APPLICATION_JSON_TYPE), Ticket.class);

        // Save the new ticket ID
        Integer ticketId = ticket.getId();

        // Verify that it is not 0
        assertNotEquals(0, (int)ticketId);

        // Validate that the rest of the data is correct
        assertEquals(ticket.getSummary(), "Test ticket summary");
        assertEquals(ticket.getStatus(), "open");
        assertEquals((long)ticket.getCreator().getId(), 1);

        // Modify and update the ticket
        ticket.setSummary("Test ticket summary UPDATED");
        target = client.target(SpiceworksApiMainServer.BASE_URI).path("tickets/update");
        target.request().put(Entity.entity(ticket, MediaType.APPLICATION_JSON_TYPE), Ticket.class);

        // Get the ticket
        target = client.target(SpiceworksApiMainServer.BASE_URI).path("tickets/" + Integer.toString(ticket.getId()));
        ticket = target.request(MediaType.APPLICATION_JSON_TYPE).get(Ticket.class);

        assertEquals(ticket.getSummary(), "Test ticket summary UPDATED");

        // Delete the ticket
        target = client.target(SpiceworksApiMainServer.BASE_URI).path("tickets/delete/" + Integer.toString(ticket.getId()));
        target.request().delete();

        // Get the ticket again
        target = client.target(SpiceworksApiMainServer.BASE_URI).path("tickets/" + Integer.toString(ticket.getId()));
        ticket = target.request(MediaType.APPLICATION_JSON_TYPE).get(Ticket.class);

        assertNull(ticket);
    }

    @Test
    public void testInvalidTicketThrowsValidationException() {
        // Create a new ticket
        Ticket ticket = new Ticket();
        //ticket.setSummary("Test ticket summary"); Summary is required - validation should fail
        ticket.setStatus("open");
        ticket.setPriority(1);
        ticket.setCreatedDate(new Date());

        User creator = new User();
        creator.setId(1);
        ticket.setCreator(creator);

        WebTarget target = client.target(SpiceworksApiMainServer.BASE_URI).path("tickets/create");

        try {
            ticket = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(ticket, MediaType.APPLICATION_JSON_TYPE), Ticket.class);
            fail("Bean validation exception should be thrown");
        }
        catch (Exception ex) {
            assertThat(ex.getCause(), instanceOf(BeanValidationException.class));
        }
    }
}
