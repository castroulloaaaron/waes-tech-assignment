package com.wearewaes.techassignment.aaroncastro.scalableweb.services.persistence;

import com.google.common.cache.Cache;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.PersistenceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.commons.lang3.Validate.*;

/**
 * Cached Persistence Storage implementation using the Google Guava Cache implementation
 * Requires the Cache instance
 * @since 1.0.0
 */
@Component
public class CachePersistenceService implements PersistenceStorage {

    private final Cache<String, PersistenceModel> cache;

    @Autowired
    public CachePersistenceService(final Cache<String, PersistenceModel> cache) {
        this.cache = cache;
    }

    /**
     * Persist into cache (Google Guava Cache version) the required PersistenceModel with the given id
     * @param id identifier of the object to persist
     * @param value content to be persisted
     * @throws IllegalArgumentException if the id is empty
     * @throws NullPointerException if the id or value is null
     */
    @Override
    public void set(final String id, final PersistenceModel value) throws IllegalArgumentException, NullPointerException {
        notBlank(id, "Id cannot be null or empty");
        notNull(value, "Value cannot be null");
        isTrue(!hasId(id), "Value is already persisted");

        cache.put(id, value);
    }

    /**
     * Retrieves the PersistenceModel from cache base on the id
     * @param id identifier of the object to be retrieved
     * @return An Optional with the PersistenceModel if was persisted, else an empty Optional
     * @throws IllegalArgumentException if the id is empty
     * @throws NullPointerException if the id or value is null
     */
    @Override
    public Optional<PersistenceModel> get(final String id) throws IllegalArgumentException, NullPointerException {
        notBlank(id, "Id cannot be null or empty");

        return Optional.ofNullable(cache.getIfPresent(id));
    }

    /**
     * Check if the PersistedModel was persisted in the cache storage base on the id
     * @param id identifier of the object to be retrieved
     * @return true if the object with the id was persisted
     * @throws IllegalArgumentException if the id is empty
     * @throws NullPointerException if the id or value is null
     */
    @Override
    public boolean hasId(final String id) throws IllegalArgumentException, NullPointerException {
        notBlank(id, "Id cannot be null or empty");

        return cache.asMap().containsKey(id);
    }
}
