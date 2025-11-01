package ru.Bodyaaaa.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Data
@Getter
@Setter
public class AnimalDTO {
    private Integer id;
    private String name;
    private Integer age;
    private String breed;
    private Integer tailLength;
    private Integer masterId;
    private List<Integer> friendIds;

}