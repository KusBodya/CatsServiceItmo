package com.catsmasters.masterservice.dto.kafka;

public class MasterDeletedEvent {
    private Long masterId;

    public MasterDeletedEvent() {
    }

    public MasterDeletedEvent(Long masterId) {
        this.masterId = masterId;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }
}
