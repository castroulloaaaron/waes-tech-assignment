package com.wearewaes.techassignment.aaroncastro.scalableweb.controllers;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.rest.Response;
import com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.TwoItemsContainer.Sides.LEFT;
import static com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.TwoItemsContainer.Sides.RIGHT;
import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.BODY;
import static com.wearewaes.techassignment.aaroncastro.scalableweb.processors.Processor.ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

/**
 * Rest Controller that accepts the User input and expose the diff comparison result base on the id parameter
 * @since version 1.0.0
 */
@RestController
@RequestMapping("/v1/diff")
public class DiffController {

    @Autowired
    private Processor persistHandler;

    /**
     * Validates and creates(persists) the left item using the id and body parameters
     * @param id identification for the item that can be compared with another item with the same id
     * @param body item to be compare
     * @return
     * 201 - Created message/code if it's a valid base64 JSON object
     * 409 - Conflict message/code if there is an item with the same id
     * 400 - Bad Request Message if the item is invalid (bad Base64 or invalid JSON object)
     */
    @PostMapping(path = "/{id}/left", consumes = TEXT_PLAIN_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response saveLeft(@PathVariable final String id, @RequestBody final String body) {
        persistHandler.process(Map.of(ID, LEFT + id, BODY, body));
        return Response.newInstance(String.format("Message with id %s was created", id));
    }

    /**
     * Validates and creates(persists) the right item using the id and body parameters
     * @param id identification for the item that can be compared with another item with the same id
     * @param body item to be compare
     * @return
     * 201 - Created message/code if it's a valid base64 JSON object
     * 409 - Conflict message/code if there is an item with the same id
     * 400 - Bad Request Message if the item is invalid (bad Base64 or invalid JSON object)
     */
    @PostMapping(path = "/{id}/right", consumes = TEXT_PLAIN_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response saveRight(@PathVariable final String id, @RequestBody final String body) {
        persistHandler.process(Map.of(ID, RIGHT + id, BODY, body));
        return Response.newInstance(String.format("Message with id %s was created", id));
    }

    /**
     * Returns the comparison result between the 2 objects (left and right) if both are present
     * @param id identification for the items to be compared
     * @return
     * 200 - OK message/code with the result operation
     * 400 - Bad Request if there is at least one missing item with the id
     */
    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object showDiffResult(@PathVariable final String id) {

        return "";
    }
}
