package com.bbc.zuber.model.fundsavailabilityresponse;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class FundsAvailabilityResponse {
    private UUID uuid;
    private UUID userUuid;
    private BigDecimal cost;
}
