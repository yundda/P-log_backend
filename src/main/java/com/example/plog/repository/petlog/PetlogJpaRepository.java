package com.example.plog.repository.petlog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.plog.repository.pet.PetEntity;

@Repository
public interface PetlogJpaRepository extends JpaRepository<PetlogEntity,Long>{
    @Query("SELECT p FROM PetlogEntity p WHERE p.pet.id = :petId")
    List<PetlogEntity> findByPetId(@Param("petId") Long petId);

    void deleteByPet(PetEntity petEntity);
}
