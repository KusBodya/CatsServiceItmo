package com.catsmasters.gateway.dto;

import java.util.Date;
import java.util.Objects;

public class MasterDto {
    private Long id;
    private String name;
    private Date birthDate;


    public MasterDto() {
    }

    public MasterDto(Long id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MasterDto masterDto = (MasterDto) o;
        return Objects.equals(id, masterDto.id) &&
                Objects.equals(name, masterDto.name) &&
                Objects.equals(birthDate, masterDto.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate);
    }

    @Override
    public String toString() {
        return "MasterDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}