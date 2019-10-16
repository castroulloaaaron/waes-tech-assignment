package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.DiffResultContainer;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.PersistenceModel;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.TwoItemsContainer;
import com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.persistence.PersistenceStorage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.ID;
import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.RESULT;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PersistenceDiffResponseFetchProcessorTest {

    @Mock
    private PersistenceStorage persistenceStorage;

    @Mock
    private DiffResultContainer diffResultContainer;

    @Mock
    private TwoItemsContainer twoItemsContainer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullMap() {
        new PersistenceDiffResultProcessor(persistenceStorage).execute(null);
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullId() {
        new PersistenceDiffResultProcessor(persistenceStorage).execute(Map.of());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWhenEmptyId() {
        new PersistenceDiffResultProcessor(persistenceStorage).execute(Map.of(ID, " "));
    }

    @Test
    public void doNothingIfResultWasNotPersisted() {
        when(persistenceStorage.hasId(anyString())).thenReturn(false);

        final Map<ParameterKeys, Object> result = new PersistenceDiffResponseFetchProcessor(persistenceStorage).execute(Map.of(ID, ID.toString()));

        verify(persistenceStorage).hasId(eq(ID.toString()));
        assertNotNull(result, "params must not be null");
    }

    @Test
    public void getResultAndStopProcessIfResultWasPersisted() {
        when(persistenceStorage.hasId(anyString())).thenReturn(true);
        when(persistenceStorage.get(anyString())).thenReturn(Optional.of(diffResultContainer));

        final Map<ParameterKeys, Object> result = new PersistenceDiffResponseFetchProcessor(persistenceStorage).execute(Map.of(ID, ID.toString()));

        verify(persistenceStorage).hasId(eq(ID.toString()));
        verify(persistenceStorage).get(eq(ID.toString()));
        assertNotNull(result, "params must not be null");
        assertNotNull(result.get(RESULT), "result must not be null");
        assertTrue(result.get(RESULT) instanceof PersistenceModel, "result must not other type than PersistenceModel");
    }

    @Test(expected = NullPointerException.class)
    public void getErrorIfNullResultWasPersisted() {
        when(persistenceStorage.hasId(anyString())).thenReturn(true);
        when(persistenceStorage.get(anyString())).thenReturn(Optional.empty());

        final Map<ParameterKeys, Object> result = new PersistenceDiffResponseFetchProcessor(persistenceStorage).execute(Map.of(ID, ID.toString()));

        verify(persistenceStorage).hasId(eq(ID.toString()));
        verify(persistenceStorage).get(eq(ID.toString()));
    }

    @Test(expected = ClassCastException.class)
    public void getErrorIfWrongResultWasPersisted() {
        when(persistenceStorage.hasId(anyString())).thenReturn(true);
        when(persistenceStorage.get(anyString())).thenReturn(Optional.of(twoItemsContainer));

        final Map<ParameterKeys, Object> result = new PersistenceDiffResponseFetchProcessor(persistenceStorage).execute(Map.of(ID, ID.toString()));

        verify(persistenceStorage).hasId(eq(ID.toString()));
        verify(persistenceStorage).get(eq(ID.toString()));
    }
}
