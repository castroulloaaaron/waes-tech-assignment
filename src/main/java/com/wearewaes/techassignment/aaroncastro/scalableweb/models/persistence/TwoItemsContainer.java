package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

public interface TwoItemsContainer extends PersistenceModel {
    String LEFT = "left-";
    String RIGHT = "right-";

    String getId();
    String getBody();

    static TwoItemsContainer newInstance(final String id, final String body) {
        return new ItemContainer(id, body);
    }
}
