package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

public interface TwoItemsContainer extends PersistenceModel {

    enum Sides {
        LEFT_SIDE("left-"),
        RIGHT_SIDE("right-");

        private final String id;

        Sides(final String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return id;
        }
    }

    String getId();
    String getBody();

    static TwoItemsContainer newInstance(final String id, final String body) {
        return new ItemContainer(id, body);
    }
}
