package com.bbc.zuber.service;

import com.bbc.zuber.model.googledistancematrix.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GoogleDistanceMatrixService {

    @Value("${google.distance.matrix.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public DistanceMatrixResponse getDistance(String origin, String destination) {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + origin +
                "&destinations=" + destination + "&key=" + apiKey;

        return restTemplate.getForObject(url, DistanceMatrixResponse.class);
    }

    public String getDistanceString(String origin, String destination) {
        DistanceMatrixResponse response = getDistance(origin, destination);

        for (DistanceMatrixRow row : response.getRows()) {
            for (DistanceMatrixElement element : row.getElements()) {
                if ("OK".equals(element.getStatus())) {
                    DistanceMatrixDistance distance = element.getDistance();
                    if (distance != null) {
                        return distance.getText();
                    }
                }
            }
        }
        return null;
    }

    public int getDistanceInt(String origin, String destination) {
        DistanceMatrixResponse response = getDistance(origin, destination);
        
        for (DistanceMatrixRow row : response.getRows()) {
            for (DistanceMatrixElement element : row.getElements()) {
                if ("OK".equals(element.getStatus())) {
                    DistanceMatrixDistance distance = element.getDistance();
                    if (distance != null) {
                        return distance.getValue();
                    }
                }
            }
        }
        return -1;
    }

    public String getDurationString(String origin, String destination) {
        DistanceMatrixResponse response = getDistance(origin, destination);

        for (DistanceMatrixRow row : response.getRows()) {
            for (DistanceMatrixElement element : row.getElements()) {
                if("OK".equals(element.getStatus())) {
                    DistanceMatrixDistance duration = element.getDistance();
                    if(duration != null) {
                        return duration.getText();
                    }
                }
            }
        }
        return null;
    }

    public int getDurationInt(String origin, String destination) {
        DistanceMatrixResponse response = getDistance(origin, destination);
        for (DistanceMatrixRow row : response.getRows()) {
            for (DistanceMatrixElement element : row.getElements()) {
                if("OK".equals(element.getStatus())) {
                    DistanceMatrixDuration duration = element.getDuration();
                    if(duration != null) {
                        return duration.getValue();
                    }
                }
            }
        }
        return -1;
    }
}
