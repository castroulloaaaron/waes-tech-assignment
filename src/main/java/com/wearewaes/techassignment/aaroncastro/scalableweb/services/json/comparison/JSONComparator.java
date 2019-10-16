package com.wearewaes.techassignment.aaroncastro.scalableweb.services.json.comparison;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.DiffResultContainer;

/**
 * Interface that declares the compare method between 2 String
 * @since version 1.0.0
 */
public interface JSONComparator {
    /**
     *
     * @param left body of the left side
     * @param right body of the right side
     * @return DiffResultContainer that indicates the result status of the comparison
     * @throws NullPointerException if left or right are null
     * @throws IllegalArgumentException if left or right are empty
     */
    DiffResultContainer compare(final String left, final String right) throws NullPointerException, IllegalArgumentException;
}
