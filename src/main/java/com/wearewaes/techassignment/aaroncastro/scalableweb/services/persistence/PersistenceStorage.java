package com.wearewaes.techassignment.aaroncastro.scalableweb.services.persistence;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.PersistenceModel;

import java.util.Optional;

/**
 * Interface that contains the basic methods get, set, hasKey that allows to store, check and retrieve a value base on
 * it's key/id
 * @since version 1.0.0
 */
public interface PersistenceStorage {
    /**
     * Stores a value base on it's key
     * @param id identifier of the object to persist
     * @param value content to be persisted
     * @throws NullPointerException if the key is null
     * @throws IllegalArgumentException if the key is empty
     * @throws IllegalArgumentException if the id was already persisted
     */
    void set(final String id, final PersistenceModel value) throws NullPointerException, IllegalArgumentException;

    /**
     * Retrieves the object persisted based on it's id
     * @param id identifier of the object to be retrieved
     * @throws NullPointerException if the key is null
     * @throws IllegalArgumentException if the key is empty
     * @return Optional.empty() if the object was not persisted, else the PersistenceModel
     */
    Optional<PersistenceModel> get(final String id) throws NullPointerException, IllegalArgumentException;

    /**
     * Checks if and id was already persisted or not
     * @param id identifier of the object that needs to be check
     * @throws NullPointerException if the key is null
     * @throws IllegalArgumentException if the key is empty
     * @return true if is persisted
     */
    boolean hasId(final String id) throws NullPointerException, IllegalArgumentException;
}
