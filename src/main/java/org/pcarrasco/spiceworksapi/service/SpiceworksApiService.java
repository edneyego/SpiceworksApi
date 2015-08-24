package org.pcarrasco.spiceworksapi.service;

import org.pcarrasco.spiceworksapi.models.User;
import org.pcarrasco.spiceworksapi.models.Ticket;

/**
 * Service layer interface of SpiceworksAPI
 */
public interface SpiceworksApiService {

    // User entity operations
    ServiceResponse<User> getUser(int userId);
    ServiceResponse<User> createUser(User user);
    ServiceResponse<User> updateUser(User user);
    ServiceResponse<User> deleteUser(int userId);

    // Ticket entity operations
    ServiceResponse<Ticket> getTicket(int ticketId);
    ServiceResponse<Ticket> createTicket(Ticket ticket);
    ServiceResponse<Ticket> updateTicket(Ticket ticket);
    ServiceResponse<Ticket> deleteTicket(int ticketId);
}
