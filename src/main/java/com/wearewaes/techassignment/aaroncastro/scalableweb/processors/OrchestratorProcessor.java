package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.STOP_FLAG;
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
     * @throws NullPointerException if params is null
     * @see com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor
     */
    @Override
    public Map<ParameterKeys, Object> process(Map<ParameterKeys, Object> params) throws NullPointerException {
        notNull(params);

        params = ImmutableMap.<ParameterKeys, Object>builder().putAll(params).build();
        for (Processor processor : processors) {
            logger.info("calling processor {}", processor);
            params = ImmutableMap
                    .<ParameterKeys, Object>builder()
                    .putAll(processor.process(params))
                    .build();

            // This allows to stop processing the processors chain
            if (params.containsKey(STOP_FLAG)) {
                break;
            }
        }
        return params;
    }
}
