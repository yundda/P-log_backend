package com.example.plog.repository.pet;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PetJpaRepository extends JpaRepository<PetEntity, Long>{

    Boolean findByNameExists(String petName);

    Optional<PetEntity> findByName(String petName);
}
