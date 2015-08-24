package org.pcarrasco.spiceworksapi.persistencetests;

import org.junit.Test;
import org.pcarrasco.spiceworksapi.models.Ticket;
import org.pcarrasco.spiceworksapi.models.User;
import org.pcarrasco.spiceworksapi.persistence.SpiceworksApiUnitOfWorkImpl;
import org.pcarrasco.spiceworksapi.persistence.TicketRepositoryImpl;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * Test ticket repository operations
 */
public class TicketRepositoryTest {

    @Test
    public void testTicketCRUD() {
        // Unit of work 1: Insert user
        SpiceworksApiUnitOfWorkImpl unitOfWork1 = new SpiceworksApiUnitOfWorkImpl();

        Ticket ticket = new Ticket();
        ticket.setSummary("Test ticket summary");
        ticket.setStatus("open");
        ticket.setPriority(1);
        ticket.setCreatedDate(new Date());

        User creator = new User();
        creator.setId(1);
        ticket.setCreator(creator);

        TicketRepositoryImpl ticketRepositoryImpl1 = unitOfWork1.getTicketRepository();
        ticketRepositoryImpl1.create(ticket);

        unitOfWork1.commit();

        // Save the new ticket ID
        Integer ticketId = ticket.getId();

        // Verify that it is not 0
        assertNotEquals(0, (int)ticketId);

        // Unit of work 2: Find the ticket we just inserted
        SpiceworksApiUnitOfWorkImpl unitOfWork2 = new SpiceworksApiUnitOfWorkImpl();
        TicketRepositoryImpl ticketRepositoryImpl2 = unitOfWork2.getTicketRepository();
        ticket = ticketRepositoryImpl2.findOne(ticketId);

        // Validate that the data is correct
        assertEquals(ticket.getSummary(), "Test ticket summary");
        assertEquals(ticket.getStatus(), "open");
        assertEquals((long)ticket.getCreator().getId(), 1);

        unitOfWork2.commit();

        // User of work 3: Update the user
        SpiceworksApiUnitOfWorkImpl unitOfWork3 = new SpiceworksApiUnitOfWorkImpl();
        TicketRepositoryImpl ticketRepositoryImpl3 = unitOfWork3.getTicketRepository();
        ticket = ticketRepositoryImpl3.findOne(ticketId);

        // Update the summary of the ticket in one SQL transaction
        ticket.setSummary("Test ticket summary UPDATED");
        ticketRepositoryImpl3.update(ticket);

        unitOfWork3.commit();

        // Unit of Work 4: Get the ticket and verify that the updates to the ticket
        // we saved to the DB
        SpiceworksApiUnitOfWorkImpl unitOfWork4 = new SpiceworksApiUnitOfWorkImpl();

        TicketRepositoryImpl ticketRepositoryImpl4 = unitOfWork4.getTicketRepository();
        ticket = ticketRepositoryImpl4.findOne(ticketId);

        unitOfWork4.commit();

        assertEquals(ticket.getSummary(), "Test ticket summary UPDATED");

        // Unit of Work 5: Delete the ticket
        SpiceworksApiUnitOfWorkImpl unitOfWork5 = new SpiceworksApiUnitOfWorkImpl();

        TicketRepositoryImpl ticketRepositoryImpl5 = unitOfWork5.getTicketRepository();
        ticket = ticketRepositoryImpl5.findOne(ticketId);
        ticketRepositoryImpl5.delete(ticket);

        unitOfWork5.commit();

        // Unit of Work 6: Find the ticket and verify it no longer exists in the DB
        SpiceworksApiUnitOfWorkImpl unitOfWork6 = new SpiceworksApiUnitOfWorkImpl();

        TicketRepositoryImpl ticketRepositoryImpl6 = unitOfWork6.getTicketRepository();
        ticket = ticketRepositoryImpl6.findOne(ticketId);

        assertNull(ticket);
    }
}
