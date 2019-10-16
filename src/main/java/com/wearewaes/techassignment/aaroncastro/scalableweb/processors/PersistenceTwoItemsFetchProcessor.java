package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.google.common.collect.ImmutableMap;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.PersistenceModel;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.TwoItemsContainer;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.persistence.PersistenceStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.TwoItemsContainer.Sides.LEFT_SIDE;
import static com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.TwoItemsContainer.Sides.RIGHT_SIDE;
import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.*;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Processor that fetches the two JSON objects to be compare
 * Requires the Persistence Storage
 * @since version 1.0.0
 */
@Component
public class PersistenceTwoItemsFetchProcessor extends AbstractProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PersistenceTwoItemsFetchProcessor.class);

    private final PersistenceStorage persistenceStorage;

    @Autowired
    public PersistenceTwoItemsFetchProcessor(final PersistenceStorage persistenceStorage) {
        this.persistenceStorage = persistenceStorage;
    }

    /**
     * Fetches the two sides (left/right) JSON object to be compare if they were previously persisted
     * @param params Map with the parameters to execute the logic base on it's service(s)
     * @return params with the 2 bodies (under left and right keys)
     * @throws NullPointerException if the params is null or the id is null
     * @throws IllegalArgumentException if the id is empty
     */
    @Override
    protected Map<ParameterKeys, Object> execute(final Map<ParameterKeys, Object> params) throws NullPointerException, IllegalArgumentException {
        notNull(params, "params map cannot be null");
        notNull(params.get(ID), "id must be present on params");
        notBlank(params.get(ID).toString(), "id cannot be null or empty");

        final String id = params.get(ID).toString();
        final String leftId = LEFT_SIDE + id;
        final String rightId = RIGHT_SIDE + id;

        if (!persistenceStorage.hasId(leftId)) {
            logger.warn("storage does not have id {}", leftId);
            throw new IllegalArgumentException("the left JSON object with id: " + id + ", was not persisted");
        }

        if (!persistenceStorage.hasId(rightId)) {
            logger.warn("storage does not have id {}", rightId);
            throw new IllegalArgumentException("the right JSON object with id: " + id + ", was not persisted");
        }

        return ImmutableMap.<ParameterKeys, Object>builder()
                .putAll(params)
                .put(LEFT, extractBody(persistenceStorage.get(leftId).orElse(null), LEFT_SIDE.toString(), id))
                .put(RIGHT, extractBody(persistenceStorage.get(rightId).orElse(null), RIGHT_SIDE.toString(), id))
                .build();
    }

    /**
     * Extracts the body from the left or right side of the id given if was previously persisted
     * @param model storage model persisted or null
     * @param twoModelSide left or right string value
     * @param id identifier of the object
     * @return the body previously stored
     * @throws NullPointerException if the model was not previously persisted
     * @throws IllegalStateException if the model has empty or null body
     * @throws ClassCastException if the persisted model is not a TwoItemsContainer object
     */
    private String extractBody(final PersistenceModel model, final String twoModelSide, final String id) throws NullPointerException, IllegalStateException, ClassCastException {
        notNull(model, "model must not be null");
        notBlank(twoModelSide, "twoModelSide must not be null or empty");
        notBlank(id, "twoModelSide' id must not be null or empty");

        if (!(model instanceof TwoItemsContainer)) {
            logger.error("the {}{} is not a TwoItemsContainer", twoModelSide, id);
            throw new ClassCastException("There was a persistent error with the " + twoModelSide + " part of the JSON for id " + id);
        }

        final String body = ((TwoItemsContainer) model).getBody();
        if (isBlank(body)) {
            logger.error("the body for {}{} is null or empty", twoModelSide, id);
            throw new IllegalStateException("The body for the " + twoModelSide + " part of the JSON for id " + id + " is null or empty");
        }

        return body;
    }
}
