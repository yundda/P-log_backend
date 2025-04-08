package com.example.plog.repository.family;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.user.UserEntity;

@Repository
public interface FamilyJpaRepository extends JpaRepository<FamilyEntity,Long>{

    Optional<UserEntity> findByUserIdAndPetId(Long userId, Long petId);

    void deleteByUserAndPet(UserEntity user, PetEntity pet);

    Optional<UserEntity> findByUser(Long requesterId, String name);


}
