package com.example.plog.repository.healthlog;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthlogJpaRepository extends JpaRepository<HealthlogEntity,Long>{
     @Query("SELECT h FROM HealthlogEntity h WHERE h.log_id.pet_id.id = :petId")
    List<HealthlogEntity> findAllByPetId(@Param("petId") Long petId);

     @Query("SELECT h FROM HealthlogEntity h " +
           " WHERE h.log_id.pet_id.id = :petId " +
           "   AND h.hospital_log = :oldLogTime")
    Optional<HealthlogEntity> findByPetIdAndHospitalLog(
        @Param("petId") Long petId,
        @Param("oldLogTime") LocalTime oldhospitalLog);
}
