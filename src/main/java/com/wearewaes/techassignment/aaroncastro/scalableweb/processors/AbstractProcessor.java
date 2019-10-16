package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.*;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Abstract class that process the execution of the Process.
 * Allows the classes that extends this processor to implements the execute method with the required logic
 * @since version 1.0.0
 */
public abstract class AbstractProcessor implements Processor {

    /**
     * Contains the entry point to execute the logic required on the Processor
     * @param params Map with the parameters to execute the logic base on it's service(s)
     * @return the new state of the parameters for the next step/processor
     */
    protected abstract Map<ParameterKeys, Object> execute(final Map<ParameterKeys, Object> params);

    /**
     * Encapsulate a general validation for the params Map and the execution call to process the business logic
     * @param params Map with the required entries for the Processor business logic execution
     * @return the new state of the params after the execution of the Processor business logic
     */
    @Override
    public final Map<ParameterKeys, Object> process(Map<ParameterKeys, Object> params) {
        notNull(params, "Parameter Map cannot be null");
        notNull(params.get(ID), "ID cannot be null");

        return execute(params);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
