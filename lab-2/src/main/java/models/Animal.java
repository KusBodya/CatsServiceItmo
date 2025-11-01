package models;

import java.util.ArrayList;


/*
import lombok.Getter;
import lombok.Setter;


@Getter
public class Animal {
    public Animal(int animalId, String animalName, String animalAge, String animalBreed, Master animalMaster) {
        id = animalId;
        name = animalName;
        age = animalAge;
        breed = animalBreed;
        master = animalMaster;
        friends = new ArrayList<Animal>();
    }
    public int id;
    @Setter
    public String name;

    final public String age;

    final public String breed;

    @Setter
    public Master master = null;

    @Setter
    public ArrayList<Animal> friends;
}
*/

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Animal {
    @Id @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String name;

    private String age;
    private String breed;

    @ManyToOne
    @JoinColumn(name = "master_id")
    @Setter
    private Master master;

    @ManyToMany
    @JoinTable(
            name = "animal_friends",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )

    @Setter
    private List<Animal> friends = new ArrayList<>();


    public Animal() {
    }

    public Animal(String animalName, String animalAge, String animalBreed, Master animalMaster) {
        name = animalName;
        age = animalAge;
        breed = animalBreed;
        master = animalMaster;
    }
}