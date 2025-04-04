package com.example.plog.repository.healthlog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthlogJpaRepository extends JpaRepository<HealthlogEntity,Long>{

}
