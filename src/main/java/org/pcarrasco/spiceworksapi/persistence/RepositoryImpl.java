package org.pcarrasco.spiceworksapi.persistence;

import org.hibernate.Session;
import org.pcarrasco.spiceworksapi.models.ApiEntity;

/**
 * Actual Hibernate database implementation of the Repository interface which defined the operations that can be performed
 * on an entity
 */
public abstract class RepositoryImpl<T extends ApiEntity> implements Repository<T> {

    // Hibernate session
    protected Session session;

    public RepositoryImpl(Session session) {
        this.session = session;
    }

    public abstract T findOne(Integer id);

    public void create(T entity) {
        session.save(entity);
    }

    public void update(T entity) {
        session.update(entity);
    }

    public void delete(T entity) {
        session.delete(entity);
    }
}
