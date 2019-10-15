package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.wearewaes.techassignment.aaroncastro.scalableweb.services.json.JSONValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Processor that checks if the body contains a valid JSON Object
 * Requires the JSONValidator service
 * @since version 1.0.0
 */
@Component
public class JSONValidatorProcessor extends AbstractProcessor {

    private final JSONValidator jsonValidator;

    @Autowired
    public JSONValidatorProcessor(final JSONValidator jsonValidator) {
        this.jsonValidator = jsonValidator;
    }

    /**
     * Contains the logic to call the JSONValidator service to check if the body is a valid JSON object
     * @param params Map with the parameters to execute the logic base on it's service(s)
     * @return the same Map if the body is a valid JSON Object
     * @throws IllegalArgumentException is the body is not a valid JSON object
     */
    @Override
    protected Map<String, String> execute(final Map<String, String> params) {
        notNull(params, "params map cannot be null");
        notBlank(params.get(ID), "id cannot be null or empty");
        notBlank(params.get(BODY), "body cannot be null or empty");

        if (!jsonValidator.isValid(params.get(BODY))) {
            throw new IllegalArgumentException("body is not a valid JSON Object");
        }

        return params;
    }
}
