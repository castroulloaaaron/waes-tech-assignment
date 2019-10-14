package com.wearewaes.techassignment.aaroncastro.scalableweb.services.base64;

/**
 * It contains a single method that must decode a base64 String if the String is valid
 * @since version 1.0.0
 */
public interface Base64Decoder {
    /**
     * Decode a Base64 String
     * @param body encode Base64 String
     * @throws NullPointerException if the body is null
     * @throws IllegalArgumentException if the body is empty or if the body is not a valid base64 String
     * @return decoded String
     */
    String decode(final String body) throws NullPointerException, IllegalArgumentException;
}
