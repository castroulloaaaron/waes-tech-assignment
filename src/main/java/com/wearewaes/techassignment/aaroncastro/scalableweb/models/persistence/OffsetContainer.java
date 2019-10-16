package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * Class that contains the comparison insight (offset/length)
 * @since version 1.0.0
 */
public class OffsetContainer implements ItemResultContainer {

    @JsonProperty("offset")
    private final int offset;

    @JsonProperty("length")
    private int length;

    @JsonCreator
    public OffsetContainer( final int offset) {
        isTrue(offset >= 0, "Offset must be bigger or equal than 0");

        this.offset = offset;
    }

    public void setLength(int length) {
        isTrue(length > 0, "length must be bigger than 0");

        this.length = length;
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }
}
