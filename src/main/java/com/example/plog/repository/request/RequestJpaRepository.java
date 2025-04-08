package com.example.plog.repository.request;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.plog.repository.Enum.Status;
import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.user.UserEntity;

@Repository
public interface RequestJpaRepository extends JpaRepository<RequestEntity,Long> {

    Optional<RequestEntity> findByInviterAndReceiverAndPet(UserEntity user, UserEntity family, PetEntity pet);
    
    Optional<RequestEntity> findByInviterAndReceiverAndPetAndStatus(UserEntity user, UserEntity owner, PetEntity pet,
            Status status);

    Optional<RequestEntity> findByInviterAndRecieverEmailAndPet(UserEntity user, String familyEmail, PetEntity pet);


}
