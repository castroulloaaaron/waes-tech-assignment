package com.wearewaes.techassignment.aaroncastro.scalableweb.services.base64;

import org.springframework.stereotype.Service;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.isBase64;

import static org.apache.commons.lang3.Validate.*;

/**
 * Class that decodes base64 String using the apache codec library
 * @since version 1.0.0
 */
@Service
public class DecoderService implements Base64Decoder {

    /**
     * Decodes the body from base64 code
     * @param body encode Base64 String
     * @return the decoded String if was a valid base64 encoded String
     * @throws NullPointerException if the body is null
     * @throws IllegalArgumentException if the body is empty
     */
    @Override
    public String decode(final String body) throws NullPointerException, IllegalArgumentException {
        notBlank(body, "body cannot be null or empty");
        isTrue(isBase64(body), "invalid Base64 Body");

        return new String(decodeBase64(body));
    }
}
