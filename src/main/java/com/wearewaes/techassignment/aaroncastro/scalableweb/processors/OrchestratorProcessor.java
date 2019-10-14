package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Processor that contains the list of steps to be executed.
 * It's the entry point of all the process that needs to take place on the application
 * @since version 1.0.0
 */
public class OrchestratorProcessor implements Processor {

    private final List<Processor> processors;

    public OrchestratorProcessor(final List<Processor> processors) {
        notNull(processors, "Processor List cannot be null");

        this.processors = processors;
    }

    /**
     * Contains the logic to call in sequence each of the Processors of it's List
     * @param params Initial Map of params that will be executed by the Processors on the List
     * @return the last state of the params after went through all the processors
     * @see com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor
     */
    @Override
    public Map<String, String> process(Map<String, String> params) {
        for (Processor processor : processors) {
            params = processor.process(params);
        }
        return params;
    }
}
