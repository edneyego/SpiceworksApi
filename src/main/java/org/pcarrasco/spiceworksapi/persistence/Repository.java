package org.pcarrasco.spiceworksapi.persistence;

import org.pcarrasco.spiceworksapi.models.ApiEntity;

/**
 * Interface for Repository which defines the operations that can be performed on an entity
 */
public interface Repository<T extends ApiEntity> {
    T findOne(Integer id);

    void create(T entity);

    void update(T entity);

    void delete(T entity);
}