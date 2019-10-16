package com.wearewaes.techassignment.aaroncastro.scalableweb.services.json.comparison;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.DiffResultContainer;

public interface JSONComparator {
    DiffResultContainer compare(final String left, final String right) throws NullPointerException, IllegalArgumentException;
}
