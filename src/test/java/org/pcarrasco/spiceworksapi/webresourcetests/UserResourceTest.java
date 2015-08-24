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
import org.pcarrasco.spiceworksapi.models.User;
import org.pcarrasco.spiceworksapi.webresource.SpiceworksApiMainServer;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.io.Console;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Test User Jersey resource
 */
public class UserResourceTest {

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
    public void testUserCRUD() {
        // Create a new user
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("LastName");
        user.setEmail("testuser@test.com");
        user.setEncryptedPassword("abcdefg1234");

        WebTarget target = client.target(SpiceworksApiMainServer.BASE_URI).path("users/create");
        user = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE), User.class);

        // Save the new user ID
        Integer userId = user.getId();

        // Verify that it is not 0
        assertNotEquals(0, (int)userId);

        // Validate that the data is correct
        assertEquals(user.getFirstName(), "Test");
        assertEquals(user.getLastName(), "LastName");
        assertEquals(user.getEmail(), "testuser@test.com");

        // Modify and update the user
        user.setLastName("LastName_Updated");
        target = client.target(SpiceworksApiMainServer.BASE_URI).path("users/update");
        target.request().put(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE), User.class);

        // Get the user
        target = client.target(SpiceworksApiMainServer.BASE_URI).path("users/" + Integer.toString(user.getId()));
        user = target.request(MediaType.APPLICATION_JSON_TYPE).get(User.class);

        assertEquals(user.getLastName(), "LastName_Updated");

        // Delete the user
        target = client.target(SpiceworksApiMainServer.BASE_URI).path("users/delete/" + Integer.toString(user.getId()));
        target.request().delete();

        // Get the user again
        target = client.target(SpiceworksApiMainServer.BASE_URI).path("users/" + Integer.toString(user.getId()));
        user = target.request(MediaType.APPLICATION_JSON_TYPE).get(User.class);

        assertNull(user);
    }

    @Test
    public void testInvalidUserThrowsValidationException() {
        // Create a new user
        User user = new User();
        user.setFirstName("Test");
        //user.setEmail("testuser@test.com");// commnet out email which is required
        user.setEncryptedPassword("abcdefg1234");

        WebTarget target = client.target(SpiceworksApiMainServer.BASE_URI).path("users/create");

        try {
            user = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(user, MediaType.APPLICATION_JSON_TYPE), User.class);
            fail("Bean validation exception should be thrown");
        }
        catch (Exception ex) {
            assertThat(ex.getCause(), instanceOf(BeanValidationException.class));
        }
    }
}
