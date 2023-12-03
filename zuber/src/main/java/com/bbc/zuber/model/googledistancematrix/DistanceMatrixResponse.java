package com.bbc.zuber.model.googledistancematrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DistanceMatrixResponse {

    @JsonProperty("destination_addresses")
    private List<String> destinationAddresses;

    @JsonProperty("origin_addresses")
    private List<String> originAddresses;

    @JsonProperty("rows")
    private List<DistanceMatrixRow> rows;

    @JsonProperty("status")
    private String status;

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public List<String> getOriginAddresses() {
        return originAddresses;
    }

    public List<DistanceMatrixRow> getRows() {
        return rows;
    }

    public String getStatus() {
        return status;
    }
}