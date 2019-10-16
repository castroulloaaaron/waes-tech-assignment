package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import java.util.Map;

/**
 * It contains a single method that allows to execute the business login inside the processor
 * Also contains the constants if the common keys inside the params Map that is been processed inside the Processors
 * @since version 1.0.0
 */
public interface Processor {

    /**
     * Common key on the params map that uses/returns the process method
     */
    enum ParameterKeys {
        ID, BODY, LEFT, RIGHT, STOP_FLAG, RESULT
    }

    /**
     * It's the entry point to call the business logic execution on each Processor
     * @param params Map of params that is been use inside the Processor
     * @return the new state of the params after the business logic execution
     * @throws NullPointerException if the params is null, or an expected key inside the map
     * @throws IllegalArgumentException id the value of the param is not valid
     */
    Map<ParameterKeys, Object> process(final Map<ParameterKeys, Object> params) throws NullPointerException, IllegalArgumentException;
}
