package ru.Bodyaaaa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.Bodyaaaa.models.Master;

import java.util.Optional;


public interface MasterRepository extends
        JpaRepository<Master, Integer>,
        JpaSpecificationExecutor<Master> {
    Optional<Master> getMasterById(Integer id);
    Optional<Master> findMasterByUsername(String username);
}