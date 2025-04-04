package com.example.plog.repository.detaillog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetaillogJpaRepository extends JpaRepository<DetaillogEntity,Long>{

}
