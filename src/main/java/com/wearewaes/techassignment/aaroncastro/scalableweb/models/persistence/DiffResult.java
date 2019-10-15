package com.wearewaes.techassignment.aaroncastro.scalableweb.models.persistence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.apache.commons.lang3.Validate.isTrue;

public class DiffResult implements DiffResultContainer {

    private final long offset;
    private final long length;

    @JsonCreator
    public DiffResult(@JsonProperty("offset") final long offset, @JsonProperty("length") final long length) {
        isTrue(offset >= 0, "Offset must be bigger than 0");
        isTrue(length >= 0, "Length must be bigger than 0");

        this.offset = offset;
        this.length = length;
    }
}
