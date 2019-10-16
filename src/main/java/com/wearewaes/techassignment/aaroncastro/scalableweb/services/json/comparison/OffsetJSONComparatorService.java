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

@Service
public class OffsetJSONComparatorService implements JSONComparator {

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

    private ResultOffsetContainer getInsight(final String left, final String right) {
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
