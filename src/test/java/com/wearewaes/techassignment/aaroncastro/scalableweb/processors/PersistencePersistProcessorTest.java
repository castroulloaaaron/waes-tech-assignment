package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.PersistenceModel;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.persistence.PersistenceStorage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.BODY;
import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PersistencePersistProcessorTest {

    private final String body = "{\"message\": \"Hi from Costa Rica!\"}";

    @Mock
    private PersistenceStorage persistenceStorage;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        doNothing().when(persistenceStorage).set(anyString(), any(PersistenceModel.class));
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullMap() {
        new PersistencePersistProcessor(persistenceStorage).execute(null);
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullId() {
        new PersistencePersistProcessor(persistenceStorage).execute(Map.of());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWhenEmptyId() {
        new PersistencePersistProcessor(persistenceStorage).execute(Map.of(ID, " "));
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullBody() {
        new PersistencePersistProcessor(persistenceStorage).execute(Map.of(ID, "id"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWhenEmptyBody() {
        new PersistencePersistProcessor(persistenceStorage).execute(Map.of(ID, "id", BODY, " "));
    }

    @Test
    public void persistsOnValidInput() {
        when(persistenceStorage.hasId(anyString())).thenReturn(false);

        Map<String, Object> result =  new PersistencePersistProcessor(persistenceStorage).execute(Map.of(ID, ID, BODY, body));

        assertNotNull(result, "params must not be null");
        assertEquals(2, result.size(), "params size must be 2");
        assertEquals(ID, result.get(ID), "id value must be id");
        assertEquals(body, result.get(BODY), "body must be the original body");
        verify(persistenceStorage).hasId(eq(ID));
        verify(persistenceStorage).set(eq(ID), any(PersistenceModel.class));
    }

    @Test(expected = IllegalStateException.class)
    public void getErrorOnPreviouslyPersistedInput() {
        when(persistenceStorage.hasId(anyString())).thenReturn(true);

        new PersistencePersistProcessor(persistenceStorage).execute(Map.of(ID, ID, BODY, body));

        verify(persistenceStorage).hasId(eq(ID));
    }
}
