package com.example.plog.repository.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(value={AuditingEntityListener.class})
@Getter

abstract class BaseEntity {

    @CreatedDate
    @Column(name="create_at", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime modDate;
}
