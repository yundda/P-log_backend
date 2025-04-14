package com.example.plog.repository.detaillog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DetaillogJpaRepository extends JpaRepository<DetaillogEntity,Long>{
    @Query("SELECT d FROM DetaillogEntity d WHERE d.log_id.pet_id.id = :petId")
    List<DetaillogEntity> findAllByPetId(@Param("petId") Long petId);
}
