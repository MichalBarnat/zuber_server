package com.bbc.zuber.controller;

import com.bbc.zuber.model.rideinfo.RideInfo;
import com.bbc.zuber.model.rideinfo.command.CreateRideInfoCommand;
import com.bbc.zuber.model.rideinfo.dto.RideInfoDto;
import com.bbc.zuber.service.RideInfoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/ride-info")
@RequiredArgsConstructor
public class RideInfoController {

    private final RideInfoService rideInfoService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<RideInfoDto>> findAll(CreateRideInfoCommand command) {
        Page<RideInfo> page = rideInfoService.findAll(modelMapper.map(command, Pageable.class));
        List<RideInfoDto> list = page.stream()
                .map(rideinfo -> modelMapper.map(rideinfo, RideInfoDto.class))
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/driver/{driverUuid}")
    public ResponseEntity<RideInfo> findByDriverUuid(@PathVariable UUID driverUuid) {
        RideInfo rideinfo = rideInfoService.findByDriverUuid(driverUuid);
        return new ResponseEntity<>(rideinfo, OK);
    }
}
