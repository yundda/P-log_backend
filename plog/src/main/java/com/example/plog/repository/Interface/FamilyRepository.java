package com.example.plog.repository.Interface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.example.plog.repository.entity.FamilyEntity;

@Repository
public interface FamilyRepository extends JpaRepository<FamilyEntity,Long>, QuerydslPredicateExecutor<FamilyEntity> {

}
