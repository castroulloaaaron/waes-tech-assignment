package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.rest;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.rest.Response;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResponseTest {

    @Test(expected = NullPointerException.class)
    public void getErrorOnNullMessageTest() {
        Response.newInstance(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorOnEmptyMessageTest() {
        Response.newInstance(" ");
    }

    @Test
    public void createResponseOnValidMessageTest() {
        final var response = Response.newInstance("message");

        assertNotNull(response, "response must not be null");
        assertEquals("message", response.getMessage(), "response message is not equals to 'message'");
    }
}
