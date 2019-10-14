package com.wearewaes.techassignment.aaroncastro.scalableweb.models.rest;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResponseTest {

    @Test(expected = NullPointerException.class)
    public void createNewInstanceWithNullMessageTest() {
        Response.newInstance(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewInstanceWithEmptyMessageTest() {
        Response.newInstance(" ");
    }

    @Test
    public void createNewInstanceWithValidMessageTest() {
        var msg = "Hi from Costa Rica";

        var response = Response.newInstance(msg);

        assertNotNull(response);
        assertEquals(msg, response.getMessage(), "response message is incorrect");
    }
}
