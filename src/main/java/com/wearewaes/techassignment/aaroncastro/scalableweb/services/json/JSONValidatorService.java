package com.wearewaes.techassignment.aaroncastro.scalableweb.services.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static org.apache.commons.lang3.Validate.notBlank;

@Service
public class JSONValidatorService implements JSONValidator {
    private static final Logger logger = LoggerFactory.getLogger(JSONValidatorService.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Checks if the body String contains a valid JSON Object
     * @param body String with the JSON String
     * @return true if the body contains a valid JSON Object
     * @throws NullPointerException if the body is null
     * @throws IllegalArgumentException if the body is empty
     */
    @Override
    public boolean isValid(final String body) {
        notBlank(body, "The body cannot null or empty");

        try {
            objectMapper.readTree(body);
        } catch(IOException e) {
            logger.info("Invalid JSON object {}", body);
            return false;
        }
        return true;
    }

}
