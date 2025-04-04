package com.example.plog.repository.Interface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.plog.repository.entity.RequestEntity;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity,Long> {

}
