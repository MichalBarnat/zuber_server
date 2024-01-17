package com.bbc.zuber.repository;

import com.bbc.zuber.model.rideinfo.RideInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RideInfoRepository extends JpaRepository<RideInfo, Long> {
    Page<RideInfo> findAll(Pageable pageable);
    List<RideInfo> findByOrderTimeBetween(LocalDateTime from, LocalDateTime to);
}
