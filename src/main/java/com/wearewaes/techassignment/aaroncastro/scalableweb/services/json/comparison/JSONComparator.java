package com.wearewaes.techassignment.aaroncastro.scalableweb.services.json.comparison;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.DiffResultContainer;

import java.util.List;

public interface JSONComparator {
    List<DiffResultContainer> compare(final String left, final String right) throws NullPointerException, IllegalArgumentException;
}
