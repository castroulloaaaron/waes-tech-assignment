package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.PersistenceModel;
import com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.persistence.PersistenceStorage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.ID;
import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.RESULT;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PersistenceDiffResultProcessorTest {

    @Mock
    private PersistenceStorage persistenceStorage;

    @Mock
    private PersistenceModel persistenceModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        doNothing().when(persistenceStorage).set(anyString(), any(PersistenceModel.class));
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

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullResult() {
        new PersistenceDiffResultProcessor(persistenceStorage).execute(Map.of(ID, ID.toString()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWhenInvalidResultType() {
        new PersistenceDiffResultProcessor(persistenceStorage).execute(Map.of(ID, ID.toString(), RESULT, RESULT));
    }

    @Test
    public void doNotSaveResultWhenItIsOnStorage() {
        when(persistenceStorage.hasId(anyString())).thenReturn(true);

        final Map<ParameterKeys, Object> result = new PersistenceDiffResultProcessor(persistenceStorage).execute(Map.of(ID, ID.toString(), RESULT, persistenceModel));

        verify(persistenceStorage).hasId(eq(ID.toString()));
        verify(persistenceStorage, never()).set(anyString(), any(PersistenceModel.class));
        assertNotNull(result, "params must not be null");
        assertNotNull(result.get(RESULT), "result must not be null");
        assertTrue(result.get(RESULT) instanceof PersistenceModel, "result must not other type than PersistenceModel");
    }

    @Test
    public void saveResultWhenItIsNotOnStorage() {
        when(persistenceStorage.hasId(anyString())).thenReturn(false);

        final Map<ParameterKeys, Object> result = new PersistenceDiffResultProcessor(persistenceStorage).execute(Map.of(ID, ID.toString(), RESULT, persistenceModel));

        verify(persistenceStorage).hasId(eq(ID.toString()));
        verify(persistenceStorage).set(eq(ID.toString()), eq(persistenceModel));
        assertNotNull(result, "params must not be null");
        assertNotNull(result.get(RESULT), "result must not be null");
        assertTrue(result.get(RESULT) instanceof PersistenceModel, "result must not other type than PersistenceModel");
    }
}
