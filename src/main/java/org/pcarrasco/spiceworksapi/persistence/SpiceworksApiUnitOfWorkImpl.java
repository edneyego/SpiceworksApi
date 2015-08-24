package org.pcarrasco.spiceworksapi.persistence;

import org.hibernate.Session;
import org.pcarrasco.spiceworksapi.utils.HibernateUtil;

/**
 * Real database implementation of unti fo work interface which maintains a list of
 * objects affected by a business transaction and coordinates the writing out of changes
 */
public class SpiceworksApiUnitOfWorkImpl implements SpiceworksApiUnitOfWork {

    private Session session;
    private TicketRepositoryImpl ticketRepository;
    private UserRepositoryImpl userRepository;

    public SpiceworksApiUnitOfWorkImpl() {
        // Start the Hibernate session when initializing the unit of work
        // since all Hibernate operations occur within a transaction
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        ticketRepository = new TicketRepositoryImpl(session);
        userRepository = new UserRepositoryImpl(session);
    }

    public TicketRepositoryImpl getTicketRepository() {
        return ticketRepository;
    }

    public UserRepositoryImpl getUserRepository() {
        return userRepository;
    }

    public void commit() {
        // Commit the Hibernate transaction to the database
        session.getTransaction().commit();
    }

    public void rollback() {
        session.getTransaction().rollback();
    }
}
