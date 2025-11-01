package com.catsmasters.catservice.dto.kafka;

public class MasterCreatedEvent {
    private Long masterId;
    private String masterName;

    public MasterCreatedEvent() {
    }

    public MasterCreatedEvent(Long masterId, String masterName) {
        this.masterId = masterId;
        this.masterName = masterName;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }
}
