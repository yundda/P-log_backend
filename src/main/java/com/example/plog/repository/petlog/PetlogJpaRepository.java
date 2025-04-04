package com.example.plog.repository.petlog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetlogJpaRepository extends JpaRepository<PetlogEntity,Long>{

}
