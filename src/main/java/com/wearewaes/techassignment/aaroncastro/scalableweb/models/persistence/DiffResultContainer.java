package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

import java.util.List;

/**
 * Interface that exposes the status and differences of the comparison operation
 * Contains also the enum with the status operation values
 * @since version 1.0.0
 */
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
        EQUALS, NOT_EQUAL, EQUAL_SIZE_NOT_EQUAL_CONTENT;
    }
}
