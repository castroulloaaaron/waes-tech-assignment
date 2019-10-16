package com.wearewaes.techassignment.aaroncastro.scalableweb.configuration;

import com.wearewaes.techassignment.aaroncastro.scalableweb.processors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configures the process paths for the scenarios (in this case 2 scenarios) that handles the application:
 * Scenario #1: Persist data
 * Scenario #2: Compare data previously persisted
 * @since version 1.0.0
 */
@Configuration
public class ProcessorsConfiguration {

    @Autowired
    private DecoderProcessor decoderProcessor;

    @Autowired
    private JSONValidatorProcessor jsonValidatorProcessor;

    @Autowired
    private PersistencePersistProcessor persistencePersistProcessor;

    @Autowired
    private PersistenceDiffResponseFetchProcessor persistenceDiffResponseFetchProcessor;

    @Autowired
    private PersistenceTwoItemsFetchProcessor persistenceTwoItemsFetchProcessor;

    @Autowired
    private JSONComparatorProcessor jsonComparatorProcessor;

    @Autowired
    private PersistenceDiffResultProcessor persistenceDiffResultProcessor;

    /**
     * Creates the Persist flow or steps to persist data
     * @return An Orchestrator processor with a list of steps that needs to be follow in order to validate and persist
     * the data (if valid)
     */
    @Bean
    public Processor persistHandler() {
        return new OrchestratorProcessor(List.of(
                decoderProcessor,
                jsonValidatorProcessor,
                persistencePersistProcessor
        ));
    }

    @Bean
    public Processor comparisonResultHandler() {
        return new OrchestratorProcessor(List.of(
                persistenceDiffResponseFetchProcessor,
                persistenceTwoItemsFetchProcessor,
                jsonComparatorProcessor,
                persistenceDiffResultProcessor
        ));
    }
}
