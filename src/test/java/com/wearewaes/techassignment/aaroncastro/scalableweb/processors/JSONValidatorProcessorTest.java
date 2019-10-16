package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.wearewaes.techassignment.aaroncastro.scalableweb.services.json.validation.JSONValidator;
import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JSONValidatorProcessorTest {

    private final String body = "{\"message\": \"Hi from Costa Rica!\"}";

    @Mock
    private JSONValidator jsonValidator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullMap() {
        new JSONValidatorProcessor(jsonValidator).execute(null);
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullId() {
        new JSONValidatorProcessor(jsonValidator).execute(Map.of());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWhenEmptyId() {
        new JSONValidatorProcessor(jsonValidator).execute(Map.of(ID, " "));
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullBody() {
        new JSONValidatorProcessor(jsonValidator).execute(Map.of(ID, "id"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWhenEmptyBody() {
        new JSONValidatorProcessor(jsonValidator).execute(Map.of(ID, "id", BODY, " "));
    }

    @Test
    public void getTrueOnValidInput() {
        when(jsonValidator.isValid(anyString())).thenReturn(true);

        Map<ParameterKeys, Object> result =  new JSONValidatorProcessor(jsonValidator).execute(Map.of(ID, ID, BODY, body));

        assertNotNull(result, "params must not be null");
        assertEquals(2, result.size(), "params size must be 2");
        assertEquals(ID, result.get(ID), "id value must be id");
        assertEquals(body, result.get(BODY), "body must be the original body");
        verify(jsonValidator).isValid(eq(body));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorOnInvalidInput() {
        when(jsonValidator.isValid(anyString())).thenReturn(false);

        new JSONValidatorProcessor(jsonValidator).execute(Map.of(ID, ID, BODY, body));

        verify(jsonValidator).isValid(eq(body));
    }
}
