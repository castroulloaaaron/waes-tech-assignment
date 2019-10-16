package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.google.common.collect.ImmutableMap;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.json.comparison.JSONComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.*;
import static org.apache.commons.lang3.Validate.*;

/**
 * Processor that handles the comparison logic between the right and left objects
 * Requires a JSONComparator service
 * @since version 1.0.0
 */
@Component
public class JSONComparatorProcessor extends AbstractProcessor {

    private final JSONComparator jsonComparator;

    @Autowired
    public JSONComparatorProcessor(final JSONComparator jsonComparator) {
        this.jsonComparator = jsonComparator;
    }

    /**
     * Calls the compare method of the JSONComparator service and stores the result on the params map
     * @param params Map with the parameters to execute the logic base on it's service(s)
     * @return a copy of the map params with the result inside
     * @throws NullPointerException if the params or ID or LEFT or RIGHT are null
     * @throws IllegalArgumentException if ID or LEFT or RIGHT are empty
     */
    @Override
    protected Map<ParameterKeys, Object> execute(final Map<ParameterKeys, Object> params) throws NullPointerException, IllegalArgumentException {
        notNull(params, "params map cannot be null");
        notNull(params.get(ID), "id must be present on params");
        notBlank(params.get(ID).toString(), "id cannot be null or empty");
        notNull(params.get(LEFT), "left must be present on params");
        notBlank(params.get(LEFT).toString(), "left cannot be null or empty");
        notNull(params.get(RIGHT), "right must be present on params");
        notBlank(params.get(RIGHT).toString(), "right cannot be null or empty");

        return ImmutableMap.<ParameterKeys, Object>builder()
                .putAll(params)
                .put(RESULT, jsonComparator.compare(params.get(LEFT).toString(), params.get(RIGHT).toString()))
                .build();
    }
}
