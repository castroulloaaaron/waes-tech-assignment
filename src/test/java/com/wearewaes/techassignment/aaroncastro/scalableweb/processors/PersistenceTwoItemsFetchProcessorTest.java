package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.DiffResultContainer;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.TwoItemsContainer;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.persistence.PersistenceStorage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.TwoItemsContainer.Sides.LEFT_SIDE;
import static com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.TwoItemsContainer.Sides.RIGHT_SIDE;
import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class PersistenceTwoItemsFetchProcessorTest {

    @Mock
    private PersistenceStorage persistenceStorage;

    @Mock
    private TwoItemsContainer twoItemsContainer;

    @Mock
    private DiffResultContainer diffResultContainer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullMap() {
        new PersistenceTwoItemsFetchProcessor(persistenceStorage).execute(null);
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullId() {
        new PersistenceTwoItemsFetchProcessor(persistenceStorage).execute(Map.of());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWhenEmptyId() {
        new PersistenceTwoItemsFetchProcessor(persistenceStorage).execute(Map.of(ID, " "));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorIfLeftSideWasNotPersisted() {
        when(persistenceStorage.hasId(eq(LEFT_SIDE + ID.toString()))).thenReturn(false);
        when(persistenceStorage.hasId(eq(RIGHT_SIDE + ID.toString()))).thenReturn(true);

        new PersistenceTwoItemsFetchProcessor(persistenceStorage).execute(Map.of(ID, ID.toString()));

        verify(persistenceStorage).hasId(eq(LEFT_SIDE + ID.toString()));
        verify(persistenceStorage, never()).get(anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorIfRightSideWasNotPersisted() {
        when(persistenceStorage.hasId(eq(LEFT_SIDE + ID.toString()))).thenReturn(true);
        when(persistenceStorage.hasId(eq(RIGHT_SIDE + ID.toString()))).thenReturn(false);

        new PersistenceTwoItemsFetchProcessor(persistenceStorage).execute(Map.of(ID, ID.toString()));

        verify(persistenceStorage).hasId(eq(RIGHT_SIDE + ID.toString()));
        verify(persistenceStorage, never()).get(anyString());
    }

    @Test(expected = ClassCastException.class)
    public void getErrorIfAnySideWasPersistedIncorrect() {
        when(persistenceStorage.hasId(eq(LEFT_SIDE + ID.toString()))).thenReturn(true);
        when(persistenceStorage.hasId(eq(RIGHT_SIDE + ID.toString()))).thenReturn(true);
        when(persistenceStorage.get(anyString())).thenReturn(Optional.of(diffResultContainer));

        new PersistenceTwoItemsFetchProcessor(persistenceStorage).execute(Map.of(ID, ID.toString()));

        verify(persistenceStorage).hasId(eq(LEFT_SIDE + ID.toString()));
        verify(persistenceStorage).hasId(eq(RIGHT_SIDE + ID.toString()));
        verify(persistenceStorage).get(anyString());
    }

    @Test(expected = IllegalStateException.class)
    public void getErrorIfAnySideWasPersistedWithEmptyBody() {
        when(persistenceStorage.hasId(eq(LEFT_SIDE + ID.toString()))).thenReturn(true);
        when(persistenceStorage.hasId(eq(RIGHT_SIDE + ID.toString()))).thenReturn(true);
        when(persistenceStorage.get(anyString())).thenReturn(Optional.of(twoItemsContainer));
        when(twoItemsContainer.getBody()).thenReturn(null);

        new PersistenceTwoItemsFetchProcessor(persistenceStorage).execute(Map.of(ID, ID.toString()));

        verify(persistenceStorage).hasId(eq(LEFT_SIDE + ID.toString()));
        verify(persistenceStorage).hasId(eq(RIGHT_SIDE + ID.toString()));
        verify(persistenceStorage).get(anyString());
    }

    @Test
    public void getSidesIfWerePersisted() {
        final String body = "{\"message\": \"Hello from Costa Rica!\"}";
        when(persistenceStorage.hasId(eq(LEFT_SIDE + ID.toString()))).thenReturn(true);
        when(persistenceStorage.hasId(eq(RIGHT_SIDE + ID.toString()))).thenReturn(true);
        when(persistenceStorage.get(anyString())).thenReturn(Optional.of(twoItemsContainer));
        when(twoItemsContainer.getBody()).thenReturn(body);

        final Map<Processor.ParameterKeys, Object> result = new PersistenceTwoItemsFetchProcessor(persistenceStorage).execute(Map.of(ID, ID.toString()));

        verify(persistenceStorage).hasId(eq(LEFT_SIDE + ID.toString()));
        verify(persistenceStorage).hasId(eq(RIGHT_SIDE + ID.toString()));
        verify(persistenceStorage).get(eq(LEFT_SIDE + ID.toString()));
        verify(persistenceStorage).get(eq(RIGHT_SIDE + ID.toString()));
        assertNotNull(result, "result must not be null");
        assertNotNull(result.get(ID), "id must not be null");
        assertNotNull(result.get(LEFT), "left must not be null");
        assertEquals(body, result.get(LEFT).toString(), "left message must be the body");
        assertNotNull(result.get(RIGHT), "right must not be null");
        assertEquals(body, result.get(RIGHT).toString(), "left message must be the body");
    }
}
