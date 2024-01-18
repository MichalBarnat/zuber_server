package com.bbc.zuber.statistics.model.user;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserStatistics {
    private String info;
    private UUID userUuid;
}
