package ru.Bodyaaaa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.Bodyaaaa.models.Animal;

import java.util.Optional;


public interface AnimalRepository extends
        JpaRepository<Animal, Integer>,
        JpaSpecificationExecutor<Animal> {
    Optional<Animal> findAnimalById(Integer id);
    void deleteAnimalById(Integer id);

}