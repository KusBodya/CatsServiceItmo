package com.catsmasters.gateway.dto.kafka;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterDeletedEvent {
    private Long masterId;
}