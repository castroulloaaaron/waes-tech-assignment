package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

/**
 * Interface that contains the enum to concatenate the persistence id for each side
 * Defines the required body and id fields
 * @since version 1.0.0
 */
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

    /**
     * Exposes the id
     * @return String object with the id
     */
    String getId();

    /**
     * Expose the content or body of the object
     * @return String with the body of the object
     */
    String getBody();

    /**
     * Allows to create a new instance of TwoItemsContainer
     * @param id id of the side object to be compare
     * @param body content of the object to be compare
     * @return new instance of the TwoItemsContainer
     */
    static TwoItemsContainer newInstance(final String id, final String body) {
        return new ItemContainer(id, body);
    }
}
