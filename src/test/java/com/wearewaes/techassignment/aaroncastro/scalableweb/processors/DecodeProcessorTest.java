package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys;
import com.wearewaes.techassignment.aaroncastro.scalableweb.services.base64.Base64Decoder;

import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DecodeProcessorTest {

    private final String decodeResponse = "{\"message\": \"Hi from Costa Rica!\"}";
    private final String encodeMessage = "eyJtZXNzYWdlIjogIkhpIGZyb20gQ29zdGEgUmljYSEifQ==";

    @Mock
    private Base64Decoder base64Decoder;

    @Before
    public void  setUp() {
        MockitoAnnotations.initMocks(this);

        when(base64Decoder.decode(anyString())).thenReturn(decodeResponse);
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullMap() {
        new DecoderProcessor(base64Decoder).execute(null);
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullId() {
        new DecoderProcessor(base64Decoder).execute(Map.of());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWhenEmptyId() {
        new DecoderProcessor(base64Decoder).execute(Map.of(ID, " "));
    }

    @Test(expected = NullPointerException.class)
    public void getErrorWhenNullBody() {
        new DecoderProcessor(base64Decoder).execute(Map.of(ID, "id"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getErrorWhenEmptyBody() {
        new DecoderProcessor(base64Decoder).execute(Map.of(ID, "id", BODY, " "));
    }

    @Test
    public void getDecodeStringOnValidInput() {
        Map<ParameterKeys, Object> result = new DecoderProcessor(base64Decoder).execute(Map.of(ID, ID, BODY, encodeMessage));

        assertNotNull(result, "params must not be null");
        assertEquals(2, result.size(), "params size must be 2");
        assertEquals(ID, result.get(ID), "id value must be id");
        assertEquals(decodeResponse, result.get(BODY), "body must be the decode message");
        verify(base64Decoder).decode(eq(encodeMessage));
    }
}
