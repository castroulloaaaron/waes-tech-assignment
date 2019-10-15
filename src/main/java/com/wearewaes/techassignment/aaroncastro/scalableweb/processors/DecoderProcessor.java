package com.wearewaes.techassignment.aaroncastro.scalableweb.processors;

import com.wearewaes.techassignment.aaroncastro.scalableweb.services.base64.Base64Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Processor that handles the base64 decoding logic for the message.
 * Requires a Base64Decoder service
 * @since version 1.0.0
 */
@Component
public class DecoderProcessor extends AbstractProcessor {

    private final Base64Decoder base64Decoder;

    @Autowired
    public DecoderProcessor(final Base64Decoder base64Decoder) {
        this.base64Decoder = base64Decoder;
    }

    /**
     * Contains the logic to call the Base64Decoder service to decode the body
     * @param params Map with the parameters to execute the logic base on it's service(s)
     * @return a new Map with the decode body and the id
     */
    @Override
    protected Map<String, String> execute(final Map<String, String> params) {
        notNull(params, "params map cannot be null");
        notBlank(params.get(ID), "id cannot be null or empty");
        notBlank(params.get(BODY), "body cannot be null or empty");

        return Map.of(ID, params.get(ID), BODY, base64Decoder.decode(params.get(BODY)));
    }
}
