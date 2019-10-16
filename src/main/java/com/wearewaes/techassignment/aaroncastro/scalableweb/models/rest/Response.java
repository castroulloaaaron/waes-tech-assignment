package com.wearewaes.techassignment.aaroncastro.scalableweb.models.rest;

import static org.apache.commons.lang3.Validate.notBlank;

/**
 * Message to be display to the user when there is an insertion of a Diff Item
 * @since version 1.0.0
 */
public class Response {
    private final String message;

    private Response(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static Response newInstance(final String message) {
        notBlank(message);

        return new Response(message);
    }
}
