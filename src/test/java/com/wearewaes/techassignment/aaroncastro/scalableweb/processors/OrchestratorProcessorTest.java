package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

public class OrchestratorProcessorTest {

    private List<Processor> processors;
    private Map<String, String> params = Map.of();

    @Before
    public void setUp() {
        processors = List.of(mock(Processor.class), mock(Processor.class), mock(Processor.class), mock(Processor.class));

        for(Processor p : processors) {
            when(p.process(anyMap())).thenReturn(params);
        }
    }

    @Test
    public void executionOfProcessorTest() {
        Map<String, String> result = new OrchestratorProcessor(processors).process(params);

        assertNotNull(result, "params map must not be null");
        for (Processor processor : processors) {
            verify(processor).process(eq(params));
        }
    }
}
