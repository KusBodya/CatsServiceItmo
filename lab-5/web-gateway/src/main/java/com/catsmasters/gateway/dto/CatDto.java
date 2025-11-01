package com.catsmasters.gateway.dto;

import java.util.Date;
import java.util.Objects;

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

    public CatDto(Long id, String name, Date birthDate, String breed,
                  String color, Long tailLength, Long ownerId) {
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

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getBreed() {
        return breed;
    }

    public String getColor() {
        return color;
    }

    public Long getTailLength() {
        return tailLength;
    }

    public Long getOwnerId() {
        return ownerId;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTailLength(Long tailLength) {
        this.tailLength = tailLength;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatDto catDto = (CatDto) o;
        return Objects.equals(id, catDto.id) &&
                Objects.equals(name, catDto.name) &&
                Objects.equals(birthDate, catDto.birthDate) &&
                Objects.equals(breed, catDto.breed) &&
                Objects.equals(color, catDto.color) &&
                Objects.equals(tailLength, catDto.tailLength) &&
                Objects.equals(ownerId, catDto.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, breed, color, tailLength, ownerId);
    }


    @Override
    public String toString() {
        return "CatDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", breed='" + breed + '\'' +
                ", color='" + color + '\'' +
                ", tailLength=" + tailLength +
                ", ownerId=" + ownerId +
                '}';
    }
}