package com.example.plog.repository.Interface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.example.plog.repository.entity.HealthlogEntity;

@Repository
public interface HealthlogRepository extends JpaRepository<HealthlogEntity,Long>, QuerydslPredicateExecutor<HealthlogEntity> {

}
