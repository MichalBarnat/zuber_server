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

    @PostMapping("/dest")
    public ResponseEntity<String> test(@RequestBody TestBody body) {
        String distance = service.getDistanceString(body.getFrom(), body.getTo());
        return ResponseEntity.ok(distance);
    }
}
