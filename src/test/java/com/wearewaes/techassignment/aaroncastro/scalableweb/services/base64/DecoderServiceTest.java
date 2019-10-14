package com.wearewaes.techassignment.aaroncastro.scalableweb.services.base64;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecoderServiceTest {

    @Test(expected = NullPointerException.class)
    public void getErrorOnNullBody() {
        new DecoderService().decode(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorOnEmptyBody() {
        new DecoderService().decode(" ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorOnInvalidBody() {
        new DecoderService().decode("^&%*");
    }

    @Test
    public void getErrorOnValidBody() {
        final String expected = "{\"message\": \"Hi from Costa Rica!\"}";

        final String decode = new DecoderService().decode("eyJtZXNzYWdlIjogIkhpIGZyb20gQ29zdGEgUmljYSEifQ==");

        assertEquals(expected, decode, "decode String must be equals to expected");
    }
}
