package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

import java.util.List;

public interface DiffResultContainer extends PersistenceModel {
    /**
     * Get the List of Items Results after the comparison between 2 Objects
     * @return the list of ItemResults that contains the results of the comparison operation
     * @throws NullPointerException if the there was not done a comparison
     */
    List<ItemResultContainer> getItemResultsContainer() throws NullPointerException;

    /**
     * Get the comparison result between 2 objects
     * @return ResultType object showing the comparison result
     * @throws NullPointerException if there was not done a comparison
     */
    ResultTypes getResultStatus() throws NullPointerException;

    /**
     * Operation assignation after the comparison operation
     */
    enum ResultTypes {
        EQUALS("Both JSON Objects are = size and = content"),
        NOT_EQUAL("JSON objects has ≠ size"),
        EQUAL_SIZE_NOT_EQUAL_CONTENT("Both JSON objects have = size but ≠ content");

        private final String description;

        ResultTypes(final String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }
}
