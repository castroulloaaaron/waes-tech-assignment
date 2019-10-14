package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import java.util.Map;

/**
 * It contains a single method that allows to execute the business login inside the processor
 * Also contains the constants if the common keys inside the params Map that is been processed inside the Processors
 * @since version 1.0.0
 */
public interface Processor {
    String ID = "id";
    String BODY = "body";

    /**
     * It's the entry point to call the business logic execution on each Processor
     * @param params Map of params that is been use inside the Processor
     * @return the new state of the params after the business logic execution
     * @throws NullPointerException if the params is null, or an expected key inside the map
     * @throws IllegalArgumentException id the value of the param is not valid
     */
    Map<String, String> process(Map<String, String> params) throws NullPointerException, IllegalArgumentException;
}
