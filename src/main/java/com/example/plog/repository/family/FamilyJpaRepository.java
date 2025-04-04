package com.example.plog.repository.family;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyJpaRepository extends JpaRepository<FamilyEntity,Long>{

}
