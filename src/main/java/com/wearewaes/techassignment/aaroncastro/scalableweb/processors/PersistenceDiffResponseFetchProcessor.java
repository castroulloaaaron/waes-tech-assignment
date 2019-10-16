package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.google.common.collect.ImmutableMap;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.DiffResultContainer;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.PersistenceModel;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.persistence.PersistenceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.*;
import static org.apache.commons.lang3.Validate.*;

/**
 * Processor that checks if the Diff Response was calculated
 * Requires the Persistence Storage
 * @since version 1.0.0
 */
@Component
public class PersistenceDiffResponseFetchProcessor extends AbstractProcessor {

    private final PersistenceStorage persistenceStorage;

    @Autowired
    public PersistenceDiffResponseFetchProcessor(final PersistenceStorage persistenceStorage) {
        this.persistenceStorage = persistenceStorage;
    }

    /**
     * Checks if the DiffResult was calculated and persisted. If so then set the stop processing flag and returns the
     * params Map with the DiffResult object inside
     * @param params Map with the parameters to execute the logic base on it's service(s)
     * @return params object with the DiffResult object inside under the STOP_FLAG key if was previously persisted, else
     * the same param map that it received
     */
    @Override
    protected Map<ParameterKeys, Object> execute(final Map<ParameterKeys, Object> params) {
        notNull(params, "params must not be null");
        notNull(params.get(ID), "id must be present on params");
        notBlank(params.get(ID).toString(), "id must not be null or empty");

        final String id = params.get(ID).toString();

        if (persistenceStorage.hasId(id)) {
            return ImmutableMap.<ParameterKeys, Object>builder()
                    .putAll(params)
                    .put(STOP_FLAG, STOP_FLAG)
                    .put(RESULT, extractResult(persistenceStorage.get(id).orElse(null), id))
                    .build();
        }

        return ImmutableMap.<ParameterKeys, Object>builder().putAll(params).build();
    }

    /**
     * Extracts the diff result previously persisted from the storage
     * @param model storage model persisted or null
     * @param id identifier of the object
     * @return the body previously stored
     * @throws NullPointerException if the model was not previously persisted
     * @throws IllegalStateException if the model has empty or null body
     * @throws ClassCastException if the persisted model is not a DiffResultContainer object
     */
    private DiffResultContainer extractResult(final PersistenceModel model, final String id) throws ClassCastException, NullPointerException, IllegalArgumentException {
        notNull(model, "model must not be null");
        notBlank(id, "id must not be empty");

        if (!(model instanceof DiffResultContainer)) {
            throw new ClassCastException("There was a persistent error with the diff result of for id " + id);
        }

        return (DiffResultContainer) model;
    }
}
