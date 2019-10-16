package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

import static org.apache.commons.lang3.Validate.notBlank;

/**
 * Class that contains the id and body content for one of the sides to be compared
 * @since version 1.0.0
 */
public class ItemContainer implements TwoItemsContainer {

    private final String id;
    private final String body;

    ItemContainer(final String id, final String body) {
        notBlank(id, "Id cannot be null or empty");
        notBlank(body, "Body cannot be null or empty");

        this.id = id;
        this.body = body;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getBody() {
        return body;
    }

}
