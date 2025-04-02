package com.example.plog.entity;

import com.example.plog.entity.Enum.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "Family")
public class FamilyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user_id;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private PetEntity pet_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

}
