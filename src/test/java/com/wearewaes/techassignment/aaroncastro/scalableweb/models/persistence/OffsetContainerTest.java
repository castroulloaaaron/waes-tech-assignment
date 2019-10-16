package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OffsetContainerTest {

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWithInvalidOffsetTest() {
        new OffsetContainer(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWithInvalidLengthTest() {
        new OffsetContainer(0).setLength(0);
    }

    @Test
    public void createOffsetContainerTest() {
        final OffsetContainer offsetContainer = new OffsetContainer(0);
        offsetContainer.setLength(1);

        assertNotNull(offsetContainer, "offsetContainer must not be null");
        assertEquals(0, offsetContainer.getOffset(), "offsetContainer offset must be 0");
        assertEquals(1, offsetContainer.getLength(), "offsetContainer offset must be 0");
    }
}
