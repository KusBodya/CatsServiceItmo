package ru.Bodyaaaa.Specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.Bodyaaaa.models.Master;

public class MasterSpecifications {

    public static Specification<Master> hasAge(Integer age) {
        return (root, query, cb) ->
                age != null ? cb.equal(root.get("age"), age) : null;
    }

    public static Specification<Master> hasName(String name) {
        return (root, query, cb) ->
                name != null ? cb.like(root.get("name"), "%" + name + "%") : null;
    }



    public static Specification<Master> withFilters(
            Integer age,
            String name
    ) {
        return Specification.where(hasAge(age)).and(hasName(name));
    }

}
