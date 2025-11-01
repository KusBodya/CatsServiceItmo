package ru.Bodyaaaa.Specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.Bodyaaaa.models.Animal;

public class AnimalSpecifications {
    public static Specification<Animal> hasBreed(String breed) {
        return (root, query, cb) ->
                breed != null ? cb.equal(root.get("breed"), breed) : null;
    }

    public static Specification<Animal> hasAge(Integer age) {
        return (root, query, cb) ->
                age != null ? cb.equal(root.get("age"), age) : null;
    }

    public static Specification<Animal> hasName(String name) {
        return (root, query, cb) ->
                name != null ? cb.like(root.get("name"), "%" + name + "%") : null;
    }



    public static Specification<Animal> withFilters(
            String breed,
            Integer age,
            String name
    ) {
        return Specification.where(hasBreed(breed))
                .and(hasAge(age))
                .and(hasName(name));
    }
}