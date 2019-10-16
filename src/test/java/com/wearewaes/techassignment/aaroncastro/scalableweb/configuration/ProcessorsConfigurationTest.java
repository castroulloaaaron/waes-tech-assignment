package com.wearewaes.techassignment.aaroncastro.scalableweb.configuration;

import com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class ProcessorsConfigurationTest {

    @Autowired
    private ProcessorsConfiguration processorsConfiguration;

    @Test
    public void createPersistHandler() {
        assertNotNull(processorsConfiguration, "processorsConfiguration must not be null");

        Processor persistHandler = processorsConfiguration.persistHandler();
        assertNotNull(persistHandler, "persistHandler must not be null");
    }

    @Test
    public void createComparisonResultHandler() {
        assertNotNull(processorsConfiguration, "processorsConfiguration must not be null");

        Processor comparisonResultHandler = processorsConfiguration.comparisonResultHandler();
        assertNotNull(comparisonResultHandler, "comparisonResultHandler must not be null");
    }
}
