package com.wearewaes.techassignment.aaroncastro.scalableweb.services.json;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JSONValidatorServiceTest {

    @Test(expected = NullPointerException.class)
    public void getErrorOnNullBody() {
        new JSONValidatorService().isValid(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorOnEmptyBody() {
        new JSONValidatorService().isValid(" ");
    }

    @Test
    public void getIsNotValidOnInvalidJSONBody() {
        final boolean actual = new JSONValidatorService().isValid("{\"message\":\"Hi from Costa Rica!\"");

        assertFalse(actual, "body must not be a valid JSON Object");
    }

    @Test
    public void getIsValidOnValidJSONBody() {
        final boolean actual = new JSONValidatorService().isValid("{\"message\": \"Hi from Costa Rica!\"}");

        assertTrue(actual, "body must be a valid JSON Object");
    }
}
