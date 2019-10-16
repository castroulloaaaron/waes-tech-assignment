package com.wearewaes.techassignment.aaroncastro.scalableweb.services.json;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.OffsetContainer;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.json.comparison.OffsetJSONComparatorService;
import org.junit.Test;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.DiffResultContainer.ResultTypes.*;
import static org.junit.jupiter.api.Assertions.*;

public class OffsetJSONComparatorServiceTest {

    @Test(expected = NullPointerException.class)
    public void getErrorOnNullLeftParameter() {
       new OffsetJSONComparatorService().compare(null, "{}");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorOnEmptyLeftParameter() {
        new OffsetJSONComparatorService().compare(" ", "{}");
    }

    @Test(expected = NullPointerException.class)
    public void getErrorOnNullRightParameter() {
        new OffsetJSONComparatorService().compare("{}", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorOnEmptyRightParameter() {
        new OffsetJSONComparatorService().compare("{}", " ");
    }

    @Test
    public void getEqualsOnSameInputs() {
        final String input = "{\"message\": \"Hi from Costa Rica!\", \"integer\": 1, \"array\": [1.9,2.89,3.765], \"object\": { \"array\": [ {\"boolean\": true}, {\"null\": null}]}}";

        var result = new OffsetJSONComparatorService().compare(input, input);

        assertEquals(EQUALS, result.getResultStatus(), "Comparison result must be equals");
        assertNotNull(result.getItemResultsContainer(), "Diff array must not be null");
        assertEquals(0, result.getItemResultsContainer().size(), "Diff array must be empty");
    }

    @Test
    public void getEqualsOnNotEqualsSize() {
        final String left = "{\"message\": \"Hi from Costa Rica!\", \"integer\": 1, \"array\": [1.9,2.89,3.765], \"object\": { \"array\": [ {\"boolean\": true}, {\"null\": null}]}}";
        final String right = "{\"message\": \"Hi from Costa Rica!\"}";

        var result = new OffsetJSONComparatorService().compare(left, right);

        assertEquals(NOT_EQUAL, result.getResultStatus(), "Comparison result must be not equals");
        assertNotNull(result.getItemResultsContainer(), "Diff array must not be null");
        assertEquals(0, result.getItemResultsContainer().size(), "Diff array must be empty");
    }

    @Test
    public void compareSingleDifferenceOneCharacterOnInputs() {
        final String left  = "{\"message\": \"Hi from Costa Rica!\", \"integer\": 1, \"array\": [1.9,2.89,3.765], \"object\": { \"array\": [ {\"boolean\": true}, {\"null\": null}]}}";
        final String right = "{\"message\": \"Hi from Costa Rica!\", \"integer\": 1, \"array\": [2.9,2.89,3.765], \"object\": { \"array\": [ {\"boolean\": true}, {\"null\": null}]}}";

        var result = new OffsetJSONComparatorService().compare(left, right);

        assertEquals(EQUAL_SIZE_NOT_EQUAL_CONTENT, result.getResultStatus(), "Comparison result must be not equals");
        assertNotNull(result.getItemResultsContainer(), "Diff array must not be null");
        assertEquals(1, result.getItemResultsContainer().size(), "Diff array must have size 1");

        assertTrue(result.getItemResultsContainer().get(0) instanceof OffsetContainer, "difference object must be instance of OffsetContainer");
        var diff = (OffsetContainer)result.getItemResultsContainer().get(0);

        // first number of the array (from 1.9 to 2.9)
        assertEquals(59, diff.getOffset(), "Diff offset must be 60");
        assertEquals(1, diff.getLength(), "Diff length must be 1");
    }

    @Test
    public void compareMultipleNearDifferencesMultipleCharactersOnInputs() {
        final String left  = "{\"message\": \"Hi from Costa Rica!\", \"integer\": 1, \"array\": [1.9,2.89,3.765], \"object\": { \"array\": [ {\"boolean\": true}, {\"null\": null}]}}";
        final String right = "{\"message\": \"Hi from Costa Rica!\", \"integer\": 1, \"array\": [2.8,3.12,4.765], \"object\": { \"array\": [ {\"boolean\": true}, {\"null\": null}]}}";

        var result = new OffsetJSONComparatorService().compare(left, right);

        assertEquals(EQUAL_SIZE_NOT_EQUAL_CONTENT, result.getResultStatus(), "Comparison result must be not equals");
        assertNotNull(result.getItemResultsContainer(), "Diff array must not be null");
        assertEquals(5, result.getItemResultsContainer().size(), "Diff array must have size 5");

        result.getItemResultsContainer().forEach(diff -> assertTrue(diff instanceof OffsetContainer, "difference object must be instance of OffsetContainer"));

        // We need to individual test each of the differences
        // array first number difference
        var diff = (OffsetContainer)result.getItemResultsContainer().get(0);
        assertEquals(59, diff.getOffset(), "Diff offset must be 59");
        assertEquals(1, diff.getLength(), "Diff length must be 1");

        // array first number decimal part difference
        diff = (OffsetContainer)result.getItemResultsContainer().get(1);
        assertEquals(61, diff.getOffset(), "Diff offset must be 61");
        assertEquals(1, diff.getLength(), "Diff length must be 1");

        // second number difference
        diff = (OffsetContainer)result.getItemResultsContainer().get(2);
        assertEquals(63, diff.getOffset(), "Diff offset must be 63");
        assertEquals(1, diff.getLength(), "Diff length must be 1");

        // second number decimals difference
        diff = (OffsetContainer)result.getItemResultsContainer().get(3);
        assertEquals(65, diff.getOffset(), "Diff offset must be 65");
        assertEquals(2, diff.getLength(), "Diff length must be 2");

        // third number difference
        diff = (OffsetContainer)result.getItemResultsContainer().get(4);
        assertEquals(68, diff.getOffset(), "Diff offset must be 68");
        assertEquals(1, diff.getLength(), "Diff length must be 1");
    }

    @Test
    public void compareMultipleDifferencesMultipleCharactersOnInputs() {
        final String left  = "{\"message\": \"Hi from Costa Rica!\", \"integer\": 1, \"array\": [1.9,2.89,3.765], \"object\": { \"array\": [ {\"boolean\": true}, {\"null\": null}]}}";
        final String right = "{\"message\": \"Hi Netherlands :D!!\", \"integer\": 2, \"array\": [1.9,2.89,3.123], \"object\": { \"array\": [ {\"boolean\": true}, {\"null\": null}]}}";

        var result = new OffsetJSONComparatorService().compare(left, right);

        assertEquals(EQUAL_SIZE_NOT_EQUAL_CONTENT, result.getResultStatus(), "Comparison result must be not equals");
        assertNotNull(result.getItemResultsContainer(), "Diff array must not be null");
        assertEquals(3, result.getItemResultsContainer().size(), "Diff array must have size 3");

        result.getItemResultsContainer().forEach(diff -> assertTrue(diff instanceof OffsetContainer, "difference object must be instance of OffsetContainer"));

        // We need to individual test each of the differences
        // message content difference
        var diff = (OffsetContainer)result.getItemResultsContainer().get(0);
        assertEquals(16, diff.getOffset(), "Diff offset must be 16");
        assertEquals(15, diff.getLength(), "Diff length must be 15");

        //integer content difference
        diff = (OffsetContainer)result.getItemResultsContainer().get(1);
        assertEquals(46, diff.getOffset(), "Diff offset must be 46");
        assertEquals(1, diff.getLength(), "Diff length must be 1");

        // decimal content difference
        diff = (OffsetContainer)result.getItemResultsContainer().get(2);
        assertEquals(70, diff.getOffset(), "Diff offset must be 70");
        assertEquals(3, diff.getLength(), "Diff length must be 3");
    }
}