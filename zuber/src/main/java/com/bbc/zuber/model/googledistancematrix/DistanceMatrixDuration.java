package com.bbc.zuber.model.googledistancematrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrixDuration {

    @JsonProperty("text")
    private String text;

    @JsonProperty("value")
    private int value;

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }
}