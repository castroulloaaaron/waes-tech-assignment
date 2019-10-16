package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.wearewaes.techassignment.aaroncastro.scalableweb.services.json.validation.JSONValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ParameterKeys.*;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Processor that checks if the body contains a valid JSON Object
 * Requires the JSONValidator service
 * @since version 1.0.0
 */
@Component
public class JSONValidatorProcessor extends AbstractProcessor {
    private static final Logger logger = LoggerFactory.getLogger(JSONValidatorProcessor.class);

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
    protected Map<ParameterKeys, Object> execute(final Map<ParameterKeys, Object> params) {
        notNull(params, "params map cannot be null");
        notNull(params.get(ID), "id must be present on params");
        notBlank(params.get(ID).toString(), "id cannot be null or empty");
        notNull(params.get(BODY), "id must be present on params");
        notBlank(params.get(BODY).toString(), "body cannot be null or empty");

        if (!jsonValidator.isValid(params.get(BODY).toString())) {
            logger.warn("invalid JSON object for id {} with body {}", params.get(ID), params.get(BODY));
            throw new IllegalArgumentException("body is not a valid JSON Object");
        }

        return params;
    }
}
