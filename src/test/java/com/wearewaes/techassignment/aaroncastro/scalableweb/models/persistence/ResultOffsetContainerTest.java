package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResultOffsetContainerTest {

    @Test(expected = NullPointerException.class)
    public void getErrorOnNullItemResultsTest() {
        new ResultOffsetContainer(null, null).getItemResultsContainer();
    }

    @Test(expected = NullPointerException.class)
    public void getErrorOnNullResultStatusTest() {
        new ResultOffsetContainer(null, null).getResultStatus();
    }

    @Test
    public void createResultOffsetContainerTest() {
        final var resultOffsetContainer = new ResultOffsetContainer(List.of(), DiffResultContainer.ResultTypes.EQUALS);

        assertNotNull(resultOffsetContainer, "resultOffsetContainer must not be null");
        assertEquals(0, resultOffsetContainer.getItemResultsContainer().size(), "itemResults must have 0 items");
        assertEquals(DiffResultContainer.ResultTypes.EQUALS, resultOffsetContainer.getResultStatus(), "resultStatus must be EQUALS");
    }
}
