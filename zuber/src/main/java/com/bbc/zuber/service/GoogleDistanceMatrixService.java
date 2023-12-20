package com.bbc.zuber.service;

import com.bbc.zuber.model.googledistancematrix.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleDistanceMatrixService {

    @Value("${google.distance.matrix.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public DistanceMatrixResponse getDistance(String origin, String destination) {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin +
                "&destinations=" + destination + "&key=" + apiKey;

        return restTemplate.getForObject(url, DistanceMatrixResponse.class);
    }

    public String getDistanceString(String origin, String destination) {
        DistanceMatrixResponse response = getDistance(origin, destination);
        if ("OK".equals(response.getStatus())) {
            DistanceMatrixRow row = response.getRows().get(0);
            DistanceMatrixElement element = row.getElements().get(0);
            DistanceMatrixDistance distance = element.getDistance();
            if (distance != null) {
                return distance.getText();
            }
        }
        return null;
    }

    public int getDistanceInt(String origin, String destination) {
        DistanceMatrixResponse response = getDistance(origin, destination);
        if ("OK".equals(response.getStatus())) {
            DistanceMatrixRow row = response.getRows().get(0);
            DistanceMatrixElement element = row.getElements().get(0);
            DistanceMatrixDistance distance = element.getDistance();
            if (distance != null) {
                return distance.getValue();
            }
        }
        return -1;
    }

    public String getDurationString(String origin, String destination) {
        DistanceMatrixResponse response = getDistance(origin, destination);
        if ("OK".equals(response.getStatus())) {
            DistanceMatrixRow row = response.getRows().get(0);
            DistanceMatrixElement element = row.getElements().get(0);
            DistanceMatrixDuration duration = element.getDuration();
            if (duration != null) {
                return duration.getText();
            }
        }
        return null;
    }

    public int getDurationInt(String origin, String destination) {
        DistanceMatrixResponse response = getDistance(origin, destination);
        if ("OK".equals(response.getStatus())) {
            DistanceMatrixRow row = response.getRows().get(0);
            DistanceMatrixElement element = row.getElements().get(0);
            DistanceMatrixDuration duration = element.getDuration();
            if (duration != null) {
                return duration.getValue();
            }
        }
        return -1;
    }
}
