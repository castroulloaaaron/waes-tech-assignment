package com.wearewaes.techassignment.aaroncastro.scalableweb.models.rest;

/**
 * Message to be display to the user when there is an insertion of a Diff Item
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
        return new Response(message);
    }
}
