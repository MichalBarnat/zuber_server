package com.bbc.zuber.test;

import com.bbc.zuber.service.GoogleDistanceMatrixService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ControllerTest {

    private final GoogleDistanceMatrixService service;

    public ControllerTest(GoogleDistanceMatrixService service) {
        this.service = service;
    }

    @PostMapping("/distance_string")
    public ResponseEntity<String> distanceString(@RequestBody TestBody body) {
        String distance = service.getDistanceString(body.getFrom(), body.getTo());
        return ResponseEntity.ok(distance);
    }

    @PostMapping("/duration_string")
    public ResponseEntity<String> durationString(@RequestBody TestBody body) {
        String duration = service.getDurationString(body.getFrom(), body.getTo());
        return ResponseEntity.ok(duration);
    }

    @PostMapping("/distance_int")
    public ResponseEntity<Integer> distanceInt(@RequestBody TestBody body) {
        Integer distance = service.getDistanceInt(body.getFrom(), body.getTo());
        return ResponseEntity.ok(distance);
    }

    @PostMapping("/duration_int")
    public ResponseEntity<Integer> durationInt(@RequestBody TestBody body) {
        Integer duration = service.getDurationInt(body.getFrom(), body.getTo());
        return ResponseEntity.ok(duration);
    }
}
