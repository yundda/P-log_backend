package com.example.plog.repository.healthlog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface HealthlogJpaRepository extends JpaRepository<HealthlogEntity,Long>{
     @Query("SELECT h FROM HealthlogEntity h WHERE h.log_id.pet_id.id = :petId")
    List<HealthlogEntity> findAllByPetId(@Param("petId") Long petId);

    @Query("SELECT h FROM HealthlogEntity h " +
    " WHERE h.log_id.pet_id.id = :petId " +
    "   AND h.hospital_log   = :oldHospitalLog")
    Optional<HealthlogEntity> findByPetIdAndHospitalLog(
    @Param("petId") Long petId,
    @Param("oldHospitalLog") LocalDateTime oldHospitalLog);

    @Modifying
    @Transactional
    @Query(
      "DELETE FROM HealthlogEntity h " +
      " WHERE h.log_id.pet_id.id   = :petId " +
      "   AND h.hospital_log        = :hospitalLog"
    )
    void deleteByPetIdAndHospitalLog(
        @Param("petId")       Long petId,
        @Param("hospitalLog") LocalDateTime hospitalLog
    );
    
}
