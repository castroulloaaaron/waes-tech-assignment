package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.TwoItemsContainer;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.persistence.PersistenceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.apache.commons.lang3.Validate.*;

/**
 * Processor that Persists an Item into the persistence storage
 * Requires a Persistence Storage Service
 * @since version 1.0.0
 */
@Component
public class PersistencePersistProcessor extends  AbstractProcessor {

    @Autowired
    private final PersistenceStorage persistenceStorage;

    public PersistencePersistProcessor(final PersistenceStorage persistenceStorage) {
        this.persistenceStorage = persistenceStorage;
    }

    /**
     * Persist the item in the storage if the id wasn't previously persisted
     * @param params Map with the parameters to execute the logic base on it's service(s)
     * @return the Map of params persisted
     * @throws IllegalStateException if the id is already on the storage
     */
    @Override
    protected Map<String, String> execute(final Map<String, String> params) {
        notNull(params, "params map cannot be null");
        notBlank(params.get(ID), "id cannot be null or empty");
        notBlank(params.get(BODY), "body cannot be null or empty");

        if (persistenceStorage.hasId(params.get(ID))) {
            throw new IllegalStateException("body is already persisted under id: " + params.get(ID));
        }

        persistenceStorage.set(params.get(ID), TwoItemsContainer.newInstance(params.get(ID), params.get(BODY)));
        return params;
    }
}
