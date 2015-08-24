package org.pcarrasco.spiceworksapi.persistence;

/**
 * Unit of work interface for Spiceworks API
 */
public interface SpiceworksApiUnitOfWork {

    TicketRepository getTicketRepository();

    UserRepository getUserRepository();

    void commit();

    void rollback();
}
