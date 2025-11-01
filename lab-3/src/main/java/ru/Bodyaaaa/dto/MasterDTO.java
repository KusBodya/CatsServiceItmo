package ru.Bodyaaaa.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.Bodyaaaa.Roles.Role;

import java.util.List;

@Data
@Getter
@Setter
public class MasterDTO {
    private Integer id;
    private String name;
    private Integer age;
    private List<Integer> petIds;

    private String role;
    private String username;
    private String password;
}
