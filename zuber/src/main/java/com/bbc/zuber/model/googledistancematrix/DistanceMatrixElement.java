package com.bbc.zuber.model.googledistancematrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrixElement {

    @JsonProperty("distance")
    private DistanceMatrixDistance distance;

    @JsonProperty("duration")
    private DistanceMatrixDuration duration;

    @JsonProperty("status")
    private String status;

    public DistanceMatrixDistance getDistance() {
        return distance;
    }

    public DistanceMatrixDuration getDuration() {
        return duration;
    }

    public String getStatus() {
        return status;
    }
}