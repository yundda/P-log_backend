package com.example.plog.repository.detaillog;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.plog.repository.Enum.Type;

import jakarta.transaction.Transactional;

@Repository
public interface DetaillogJpaRepository extends JpaRepository<DetaillogEntity,Long>{
    @Query("SELECT d FROM DetaillogEntity d WHERE d.log_id.pet_id.id = :petId")
    List<DetaillogEntity> findAllByPetId(@Param("petId") Long petId);

    @Query("SELECT d FROM DetaillogEntity d " +
           " WHERE d.log_id.pet_id.petName = :petName" +
           "   AND d.log_id.user_id.id      = :userId" +
           "   AND d.log_id.type            = :type" +
           "   AND d.log_time               = :logTime")
    Optional<DetaillogEntity> findByPetNameAndUserIdAndTypeAndLogTime(
        @Param("petName") String    petName,
        @Param("userId")   Long      userId,
        @Param("type")     Type      type,
        @Param("logTime")  LocalDateTime logTime
    );

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(
      "DELETE FROM DetaillogEntity d " +
      " WHERE d.log_id.id IN ( " +
      "   SELECT pl.id FROM PetlogEntity pl " +
      "    WHERE pl.pet_id.id = :petId " +
      " ) " +
      "   AND d.log_time = :logTime"
    )
    void deleteByPetIdAndLogTime(
        @Param("petId")   Long             petId,
        @Param("logTime") LocalDateTime   logTime
    );

    @Modifying
    @Query("delete from DetailLogEntity d where d.pet.id = :petId")
    void deleteAllByPetId(@Param("petId") Long petId);
}
