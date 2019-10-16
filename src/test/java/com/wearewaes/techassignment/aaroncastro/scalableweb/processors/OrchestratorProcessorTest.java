package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import org.junit.Test;

import com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys;
import java.util.List;
import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.STOP_FLAG;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

public class OrchestratorProcessorTest {

    private List<Processor> processors;
    private Map<ParameterKeys, Object> params = Map.of();
    private Map<ParameterKeys, Object> paramsWithStopFlag = Map.of(STOP_FLAG, STOP_FLAG);

    @Test
    public void executionOfProcessorTest() {
        processors = List.of(mock(Processor.class), mock(Processor.class), mock(Processor.class), mock(Processor.class));
        processors.forEach(processor -> when(processor.process(anyMap())).thenReturn(params));

        Map<ParameterKeys, Object> result = new OrchestratorProcessor(processors).process(params);

        assertNotNull(result, "params map must not be null");
        processors.forEach(processor -> verify(processor).process(eq(params)));
    }

    @Test
    public void executionOfProcessorWithStopFlagTest() {
        processors = List.of(mock(Processor.class), mock(Processor.class), mock(Processor.class), mock(Processor.class));
        when(processors.get(0).process(anyMap())).thenReturn(paramsWithStopFlag);

        Map<ParameterKeys, Object> result = new OrchestratorProcessor(processors).process(params);

        assertNotNull(result, "params map must not be null");
        verify(processors.get(0)).process(eq(params));
        for (int i = 1; i < processors.size(); i++) {
            verify(processors.get(i), never()).process(anyMap());
        }
    }
}
