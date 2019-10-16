package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Class that contains the comparison result.
 * Requires a not null List of ItemResultContainer and a not null ResultTypes
 * @since version 1.0.0
 */
public class ResultOffsetContainer implements DiffResultContainer {

    private final List<ItemResultContainer> itemResultContainers;
    private final ResultTypes resultType;

    public ResultOffsetContainer(final List<ItemResultContainer> itemResultContainers, final ResultTypes resultType) {
        this.itemResultContainers = itemResultContainers;
        this.resultType = resultType;
    }

    @Override
    public List<ItemResultContainer> getItemResultsContainer() throws NullPointerException {
        notNull(itemResultContainers);

        return itemResultContainers;
    }

    @Override
    public ResultTypes getResultStatus() throws NullPointerException {
        notNull(resultType);

        return resultType;
    }

}
