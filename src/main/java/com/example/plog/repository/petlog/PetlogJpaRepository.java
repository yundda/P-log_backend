package com.example.plog.repository.petlog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PetlogJpaRepository extends JpaRepository<PetlogEntity,Long>{
    @Query("SELECT p FROM PetlogEntity p WHERE p.pet_id = :petId")
    List<PetlogEntity> findByPetId(@Param("petId") Long petId);

    @Modifying
    @Query("delete from DetailLogEntity d where d.pet.id = :petId")
    void deleteAllByPetId(@Param("petId") Long petId);
}
