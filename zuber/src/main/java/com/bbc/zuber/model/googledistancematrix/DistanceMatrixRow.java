package com.bbc.zuber.model.googledistancematrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrixRow {

    @JsonProperty("elements")
    private List<DistanceMatrixElement> elements;

    public List<DistanceMatrixElement> getElements() {
        return elements;
    }
}