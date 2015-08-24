package org.pcarrasco.spiceworksapi.persistence;

import org.hibernate.Session;
import org.pcarrasco.spiceworksapi.models.User;

/**
 * Actual Hibernate ORM implementation of the Ticket repository
 */
public class UserRepositoryImpl extends RepositoryImpl<User> implements UserRepository {

    public UserRepositoryImpl(Session session) {
        super(session);
    }

    @Override
    public User findOne(Integer id) {
        return (User) session.get(User.class, id);
    }
}
