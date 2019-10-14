package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

import static org.apache.commons.lang3.Validate.notBlank;

public class ItemContainer implements TwoItemsContainer {

    private final String id;
    private final String body;

    public ItemContainer(final String id, final String body) {
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
