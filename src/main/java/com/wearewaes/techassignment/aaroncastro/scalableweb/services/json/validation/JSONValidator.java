package com.wearewaes.techassignment.aaroncastro.scalableweb.services.json.validation;

/**
 * Interface that contains a single method that transforms a String to the Jackson JsonNode implementation if the body
 * is valid
 * @since version 1.0.0
 */
public interface JSONValidator {
    /**
     * Validates that the body is a valid JSON object
     * @param body String with the JSON String
     * @return true if is a valid JSON object
     * @throws NullPointerException     if the body is null
     * @throws IllegalArgumentException if the body is not a valid stringify JSON Object
     * @see com.fasterxml.jackson.databind.JsonNode
     */
    boolean isValid(final String body) throws NullPointerException, IllegalArgumentException;
}
