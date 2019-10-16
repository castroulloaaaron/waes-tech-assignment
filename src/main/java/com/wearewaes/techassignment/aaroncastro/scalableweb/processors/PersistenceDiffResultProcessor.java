package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.google.common.collect.ImmutableMap;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.PersistenceModel;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.persistence.PersistenceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.ID;
import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.RESULT;
import static org.apache.commons.lang3.Validate.*;

/**
 * Processor that handles the persistence of the comparison result
 * Requires the PersistenceStorage service
 * @since version 1.0.0
 */
@Component
public class PersistenceDiffResultProcessor extends AbstractProcessor {

    private final PersistenceStorage persistenceStorage;

    @Autowired
    public PersistenceDiffResultProcessor(final PersistenceStorage persistenceStorage) {
        this.persistenceStorage = persistenceStorage;
    }

    /**
     * Persists the comparison result if it is not already on the storage
     * @param params Map with the parameters to execute the logic base on it's service(s)
     * @return a copy of the params map
     * @throws NullPointerException if the params or ID or RESULT are null
     * @throws IllegalArgumentException if the ID is empty or RESULT is not instance of PersistenceModel
     */
    @Override
    protected Map<ParameterKeys, Object> execute(final Map<ParameterKeys, Object> params) throws NullPointerException, IllegalArgumentException {
        notNull(params, "params must not be null");
        notNull(params.get(ID), "id must be on params");
        notBlank(params.get(ID).toString(), "id can not be null or empty");
        notNull(params.get(RESULT), "result must not be null");
        isTrue(params.get(RESULT) instanceof PersistenceModel, "");

        final String id = params.get(ID).toString();

        if (!persistenceStorage.hasId(id)) {
            persistenceStorage.set(id, (PersistenceModel) params.get(RESULT));
        }

        return ImmutableMap.<ParameterKeys, Object>builder().putAll(params).build();
    }
}
