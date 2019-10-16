package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.DiffResultContainer;
import com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.json.comparison.JSONComparator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JSONComparatorProcessorTest {

    @Mock
    private JSONComparator jsonComparator;

    @Mock
    private DiffResultContainer diffResultContainer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(jsonComparator.compare(anyString(), anyString())).thenReturn(diffResultContainer);
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullMap() {
        new JSONComparatorProcessor(jsonComparator).execute(null);
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullId() {
        new JSONComparatorProcessor(jsonComparator).execute(Map.of());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWhenEmptyId() {
        new JSONComparatorProcessor(jsonComparator).execute(Map.of(ID, " "));
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullLeft() {
        new JSONComparatorProcessor(jsonComparator).execute(Map.of(ID, ID.toString(), LEFT, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWhenEmptyLeft() {
        new JSONComparatorProcessor(jsonComparator).execute(Map.of(ID, ID.toString(), LEFT, " "));
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullRight() {
        new JSONComparatorProcessor(jsonComparator).execute(Map.of(ID, ID.toString(), LEFT, LEFT.toString(), RIGHT, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWhenEmptyRight() {
        new JSONComparatorProcessor(jsonComparator).execute(Map.of(ID, ID.toString(), LEFT, LEFT.toString(), RIGHT, " "));
    }

    @Test
    public void executeComparisonTest() {
        final Map<Processor.ParameterKeys, Object> result = new JSONComparatorProcessor(jsonComparator).execute(Map.of(ID, ID.toString(), LEFT, LEFT.toString(), RIGHT, RIGHT.toString()));

        verify(jsonComparator).compare(LEFT.toString(), RIGHT.toString());
        assertTrue(result.containsKey(RESULT));
        assertNotNull(result.get(RESULT));
    }
}
