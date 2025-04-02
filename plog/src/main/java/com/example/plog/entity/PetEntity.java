package com.example.plog.entity;

import java.time.LocalDate;

import com.example.plog.entity.Enum.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "PetProfile")
public class PetEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "species", nullable = false)
    private String species;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "weight", nullable = false)
    private double weight;

    @Column(name = "photo")
    private String photo;

}
