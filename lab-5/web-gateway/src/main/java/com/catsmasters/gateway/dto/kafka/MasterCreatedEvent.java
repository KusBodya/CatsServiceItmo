package com.catsmasters.gateway.dto.kafka;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterCreatedEvent {
    private Long masterId;
    private String masterName;
}