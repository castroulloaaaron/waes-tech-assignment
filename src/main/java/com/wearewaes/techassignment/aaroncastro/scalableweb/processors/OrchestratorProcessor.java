package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Processor that contains the list of steps to be executed.
 * It's the entry point of all the process that needs to take place on the application
 * @since version 1.0.0
 */
public class OrchestratorProcessor implements Processor {
    private static final Logger logger = LoggerFactory.getLogger(OrchestratorProcessor.class);

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
    public Map<String, Object> process(Map<String, Object> params) {
        for (Processor processor : processors) {
            logger.info("calling processor {}", processor);
            params = processor.process(params);

            // This allows to stop processing the processors chain
            if (params.containsKey(STOP_FLAG)) {
                break;
            }
        }
        return params;
    }
}
