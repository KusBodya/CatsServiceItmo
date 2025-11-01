package com.catsmasters.catservice.dto;

import java.util.Date;

public class CatDto {
    private Long id;
    private String name;
    private Date birthDate;
    private String breed;
    private String color;
    private Long tailLength;
    private Long ownerId;

    public CatDto() {
    }

    public CatDto(Long id, String name, Date birthDate, String breed, String color, Long tailLength, Long ownerId) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.tailLength = tailLength;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getTailLength() {
        return tailLength;
    }

    public void setTailLength(Long tailLength) {
        this.tailLength = tailLength;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
