package com.wearewaes.techassignment.aaroncastro.scalableweb.services.json.comparison;

import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.DiffResultContainer;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.ItemResultContainer;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.OffsetContainer;
import com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence.ResultOffsetContainer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

/**
 * Class that compares the 2 String representation objects
 * @since version 1.0.0
 */
@Service
public class OffsetJSONComparatorService implements JSONComparator {

    /**
     * Compares the 2 objects String representation.
     * If they are String equals returns EQUALS with an empty list of ItemResultContainer
     * If they are size different returns NOT_EQUALS with an empty list of ItemResultContainer
     * If they are size equals but content different, then returns EQUAL_SIZE_NOT_EQUAL_CONTENT and calculates those
     * differences
     * @param left body of the left side
     * @param right body of the right side
     * @return DiffResultContainer object with the comparison operation status
     * @throws NullPointerException if left or right are null
     * @throws IllegalArgumentException if left or right are empty
     */
    @Override
    public DiffResultContainer compare(final String left, final String right) throws NullPointerException, IllegalArgumentException {
        notBlank(left, "Left side must not be empty");
        notBlank(right, "Right side must not be empty");

        if (left.equals(right)) {
            return new ResultOffsetContainer(List.of(), DiffResultContainer.ResultTypes.EQUALS);
        }

        if (left.length() != right.length()) {
            return new ResultOffsetContainer(List.of(), DiffResultContainer.ResultTypes.NOT_EQUAL);
        }

        return getInsight(left, right);
    }

    /**
     * Compares 2 String of equal size and get the diferrences by offset (point where the ≠ start and length of the ≠)
     * @param left String object to be compare
     * @param right String object to be compare
     * @return ResultOffsetContainer object with EQUAL_SIZE_NOT_EQUAL_CONTENT status and the list of at least on ≠
     * @throws IllegalArgumentException if the left and right part are not equal size
     */
    private ResultOffsetContainer getInsight(final String left, final String right) throws IllegalArgumentException {
        isTrue(left.length() == right.length(), "String must have the same size");

        List<ItemResultContainer> diffs = new ArrayList<>();
        OffsetContainer offsetContainer = null;

        for (int i = 0; i < left.length(); i++) {
            boolean equalCharacters = left.charAt(i) == right.charAt(i);
            if (!equalCharacters && offsetContainer == null) {
                // Here we need to start 'recoding' since there is a diff
                offsetContainer = new OffsetContainer(i);
            } else if (equalCharacters && offsetContainer != null) {
                // we need to store the offsetContainer and set it as null again
                offsetContainer.setLength(i - offsetContainer.getOffset());
                diffs.add(offsetContainer);
                offsetContainer = null;
            }
        }

        return new ResultOffsetContainer(diffs, DiffResultContainer.ResultTypes.EQUAL_SIZE_NOT_EQUAL_CONTENT);
    }
}
