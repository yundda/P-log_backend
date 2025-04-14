package com.example.plog.repository.family;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.plog.repository.Enum.Role;
import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.user.UserEntity;

@Repository
public interface FamilyJpaRepository extends JpaRepository<FamilyEntity,Long>{

    void deleteByUserAndPet(UserEntity user, PetEntity pet);

    @Query("SELECT f.pet FROM FamilyEntity f WhERE f.user.id = :userId AND f.pet.petName = :name")
    Optional<PetEntity> findByUserIdAndPetName(Long userId, String name);

    @Query("SELECT f.user FROM FamilyEntity f WHERE f.pet.petName = :name AND f.role = :role ")
    List<UserEntity> findByPetNameAndRole(String name, Role role);

    List<FamilyEntity> findByUserId(Long userId);

    void deleteByPet(PetEntity pet);

}
