package com.example.plog.repository.petlog;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.plog.repository.pet.PetEntity;

@Repository
public interface PetlogJpaRepository extends JpaRepository<PetlogEntity,Long>{
        
}
