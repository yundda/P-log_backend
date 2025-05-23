package com.example.plog.repository.detaillog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.plog.repository.Enum.Type;
import com.example.plog.repository.pet.PetEntity;

import jakarta.transaction.Transactional;

@Repository
public interface DetaillogJpaRepository extends JpaRepository<DetaillogEntity,Long>{
    @Query("SELECT d FROM DetaillogEntity d WHERE d.log.pet.id = :petId")
    List<DetaillogEntity> findAllByPetId(@Param("petId") Long petId);

    @Query("SELECT d FROM DetaillogEntity d " +
           " WHERE d.log.pet.petName = :petName" +
           "   AND d.log.user.id      = :userId" +
           "   AND d.log.type            = :type" +
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
      " WHERE d.log.id IN ( " +
      "   SELECT pl.id FROM PetlogEntity pl " +
      "    WHERE pl.pet.id = :petId " +
      " ) " +
      "   AND d.log_time = :logTime"
    )
    void deleteByPetIdAndLogTime(
        @Param("petId")   Long             petId,
        @Param("logTime") LocalDateTime   logTime
    );
    

    void deleteByLog_Pet(PetEntity petEntity);

    
}
