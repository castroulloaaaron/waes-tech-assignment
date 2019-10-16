package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.google.common.collect.ImmutableMap;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.json.comparison.JSONComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.*;
import static org.apache.commons.lang3.Validate.*;

@Component
public class JSONComparatorProcessor extends AbstractProcessor {

    private final JSONComparator jsonComparator;

    @Autowired
    public JSONComparatorProcessor(final JSONComparator jsonComparator) {
        this.jsonComparator = jsonComparator;
    }

    @Override
    protected Map<ParameterKeys, Object> execute(final Map<ParameterKeys, Object> params) {
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
