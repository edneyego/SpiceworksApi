package org.pcarrasco.spiceworksapi.service;

import org.pcarrasco.spiceworksapi.models.Ticket;
import org.pcarrasco.spiceworksapi.models.User;
import org.pcarrasco.spiceworksapi.persistence.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Actual implementation of Service layer
 */
public class SpiceworksApiServiceImpl implements SpiceworksApiService {

    private final SpiceworksApiUnitOfWork unitOfWork;

    // We only need one instance of the ValidatorFactory per application
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Inject
    public SpiceworksApiServiceImpl(SpiceworksApiUnitOfWork unitOfWork) {

        // The unit of work is injected into the service layer
        this.unitOfWork = unitOfWork;
    }

    /**
     * Get a user from the DB with the give user id
     * @param userId
     * @return
     */
    @Override
    public ServiceResponse<User> getUser(int userId) {
        ServiceResponse<User> response = new ServiceResponse<User>();

        try {
            UserRepository userRepository = unitOfWork.getUserRepository();
            response.setData(userRepository.findOne(userId));
        }
        catch (Exception ex) {
            response.setHasException(true);
            response.setExceptionMessage(ex.getMessage());
        }

        return response;
    }

    /**
     * Create a new user in the DB using the given user entity
     * @param user
     * @return
     */
    @Override
    public ServiceResponse<User> createUser(User user) {
        ServiceResponse<User> response = new ServiceResponse<User>();

        try {
            // Validate the user object
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

            if (constraintViolations.size() > 0) {
                response.setHasValidationError(true);
                response.setValidationMessage(constraintViolations.iterator().next().getMessage());
            }
            else {
                UserRepository userRepository = unitOfWork.getUserRepository();
                userRepository.create(user);
                unitOfWork.commit();

                response.setData(user);
            }
        }
        catch (Exception ex) {
            response.setHasException(true);
            response.setExceptionMessage(ex.getMessage());
        }

        return response;
    }

    /**
     * Update the user in the DB using the given user entity
     * @param user
     * @return
     */
    @Override
    public ServiceResponse<User> updateUser(User user) {
        ServiceResponse<User> response = new ServiceResponse<User>();

        try {
            // Validate the user object
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

            if (constraintViolations.size() > 0) {
                response.setHasValidationError(true);
                response.setValidationMessage(constraintViolations.iterator().next().getMessage());
            }
            else {
                UserRepository userRepository = unitOfWork.getUserRepository();
                userRepository.update(user);
                unitOfWork.commit();

                response.setData(user);
            }
        }
        catch (Exception ex) {
            response.setHasException(true);
            response.setExceptionMessage(ex.getMessage());
        }

        return response;
    }

    /**
     * Delete a user from the DB with the given user id
     * @param userId
     * @return
     */
    @Override
    public ServiceResponse<User> deleteUser(int userId) {
        ServiceResponse<User> response = new ServiceResponse<User>();

        try {
            UserRepository userRepository = unitOfWork.getUserRepository();
            User user = new User();
            user.setId(userId);
            userRepository.delete(user);
            unitOfWork.commit();
        }
        catch (Exception ex) {
            response.setHasException(true);
            response.setExceptionMessage(ex.getMessage());
        }

        return response;
    }

    /**
     * Get a ticket from the DB with the given ticket id
     * @param ticketId
     * @return
     */
    @Override
    public ServiceResponse<Ticket> getTicket(int ticketId) {
        ServiceResponse<Ticket> response = new ServiceResponse<Ticket>();

        try {
            TicketRepository ticketRepository = unitOfWork.getTicketRepository();
            response.setData(ticketRepository.findOne(ticketId));
        }
        catch (Exception ex) {
            response.setHasException(true);
            response.setExceptionMessage(ex.getMessage());
        }

        return response;
    }

    /**
     * Create a new ticket in the DB using the given ticket entity
     * @param ticket
     * @return
     */
    @Override
    public ServiceResponse<Ticket> createTicket(Ticket ticket) {
        ServiceResponse<Ticket> response = new ServiceResponse<Ticket>();

        try {
            // Validate the ticket object
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Ticket>> violations = validator.validate(ticket);

            if (violations.size() > 0) {
                response.setHasValidationError(true);
                response.setValidationMessage(violations.iterator().next().getMessage());
            }
            else {
                TicketRepository ticketRepository = unitOfWork.getTicketRepository();
                ticketRepository.create(ticket);
                unitOfWork.commit();

                response.setData(ticket);
            }
        }
        catch (Exception ex) {
            response.setHasException(true);
            response.setExceptionMessage(ex.getMessage());
        }

        return response;
    }

    /**
     * Update a tikcet in the DB using the given ticket entity
     * @param ticket
     * @return
     */
    @Override
    public ServiceResponse<Ticket> updateTicket(Ticket ticket) {
        ServiceResponse<Ticket> response = new ServiceResponse<Ticket>();

        try {
            // Validate the ticket object
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Ticket>> violations = validator.validate(ticket);

            if (violations.size() > 0) {
                response.setHasValidationError(true);
                response.setValidationMessage(violations.iterator().next().getMessage());
            }
            else {
                TicketRepository ticketRepository = unitOfWork.getTicketRepository();
                ticketRepository.update(ticket);
                unitOfWork.commit();

                response.setData(ticket);
            }
        }
        catch (Exception ex) {
            response.setHasException(true);
            response.setExceptionMessage(ex.getMessage());
        }

        return response;
    }

    /**
     * Delete a ticket from the DB with the given the ticket id
     * @param ticketId
     * @return
     */
    @Override
    public ServiceResponse<Ticket> deleteTicket(int ticketId) {
        ServiceResponse<Ticket> response = new ServiceResponse<Ticket>();

        try {
            TicketRepository ticketRepository = unitOfWork.getTicketRepository();
            Ticket ticket = new Ticket();
            ticket.setId(ticketId);
            ticketRepository.delete(ticket);
            unitOfWork.commit();
        }
        catch (Exception ex) {
            response.setHasException(true);
            response.setExceptionMessage(ex.getMessage());
        }

        return response;
    }
}
