/*
package models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class Master {
    public Master(String masterName, String masterAge) {
        name = masterName;
        age = masterAge;
    }

    String name;

    String age;

    @Setter
    ArrayList<Animal> Pets = new ArrayList<>();


}
*/

package models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Master {
    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String age;

    @OneToMany(mappedBy = "master", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<Animal> pets = new ArrayList<>();

    // Пустой конструктор для Hibernate
    public Master() {
    }

    public Master(String masterName, String masterAge) {
        name = masterName;
        age = masterAge;
    }
}
