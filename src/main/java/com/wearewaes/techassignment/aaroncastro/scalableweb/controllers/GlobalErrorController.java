package com.wearewaes.techassignment.aaroncastro.scalableweb.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller that intercepts known exceptions and updates the message and http code base on the failure scenario
 */
@RestControllerAdvice
public class GlobalErrorController {

    /**
     * Handles NullPointerException and IllegalArgumentException sending the 400 error code Bad Request
     * @param ex Exception thrown by any process or service
     * @param response HttpServletResponse object that contains the information to be sent to the user
     * @throws IOException if the sendError call fails
     */
    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
    public void handlePreconditionViolation(final Exception ex, final HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    /**
     * Handles IllegalStateException sending the 409 error code Conflict
     * @param ex Exception thrown by any process or service
     * @param response HttpServletResponse object that contains the information to be sent to the user
     * @throws IOException if the sendError call fails
     */
    @ExceptionHandler({IllegalStateException.class})
    public void handleDataStateViolation(final Exception ex, final HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    /**
     * Handles ClassCastException sending the 500 error code Conflict
     * @param ex Exception thrown by any process or service
     * @param response HttpServletResponse object that contains the information to be sent to the user
     * @throws IOException if the sendError call fails
     */
    @ExceptionHandler({ClassCastException.class})
    public void handleDataTypeViolation(final Exception ex, final HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
    }
}
