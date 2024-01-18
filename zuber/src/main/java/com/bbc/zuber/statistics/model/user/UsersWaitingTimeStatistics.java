package com.bbc.zuber.statistics.model.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersWaitingTimeStatistics {
    private String info;
    private Double averageWaitingTime;
}
