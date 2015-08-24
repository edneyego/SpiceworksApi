package org.pcarrasco.spiceworksapi.persistence;

import org.hibernate.Session;
import org.pcarrasco.spiceworksapi.models.Ticket;

/**
 * Actual Hibernate ORM implementation of the Ticket repository
 */
public class TicketRepositoryImpl extends RepositoryImpl<Ticket> implements TicketRepository {

    public TicketRepositoryImpl(Session session) {
        super(session);
    }

    @Override
    public Ticket findOne(Integer id) {
        return (Ticket) session.get(Ticket.class, id);
    }
}
