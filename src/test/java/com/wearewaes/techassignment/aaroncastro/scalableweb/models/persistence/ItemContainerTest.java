package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ItemContainerTest {

    private final String BODY = "{\"message\":\"Hi from Costa Rica!\"}";
    private final String ID = "id";

    @Test(expected = NullPointerException.class)
    public void createWithNullIdTest() {
        TwoItemsContainer.newInstance(null, BODY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyIdTest() {
        TwoItemsContainer.newInstance("", BODY);
    }

    @Test(expected = NullPointerException.class)
    public void createWithNullBodyTest() {
        TwoItemsContainer.newInstance(ID, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyBodyTest() {
        TwoItemsContainer.newInstance(ID, " ");
    }

    @Test
    public void createWithValidDataTest() {
        final var item = TwoItemsContainer.newInstance(ID, BODY);

        assertNotNull(item, "item should not be null");
        assertEquals(BODY, item.getBody(), "body is incorrect");
        assertEquals(ID, item.getId(), "id is incorrect");
    }
}
