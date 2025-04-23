package com.example.plog.repository.pet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.plog.repository.BaseEntity;
import com.example.plog.repository.Enum.Gender;
import com.example.plog.repository.family.FamilyEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "pet_profile")
public class PetEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pet_name", nullable = false)
    private String petName;

    @Column(name = "pet_species", nullable = false)
    private String petSpecies;

    @Column(name = "pet_breed", nullable = false)
    private String petBreed;

    @Column(name = "pet_birthday", nullable = false)
    private LocalDate petBirthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "pet_gender", nullable = false)
    private Gender petGender;

    @Column(name = "pet_weight", nullable = false)
    private Double petWeight;

    @Column(name = "pet_photo",length = 1024)
    private String petPhoto;

    @OneToMany(mappedBy = "pet")
    @Builder.Default
    private List<FamilyEntity> familyList = new ArrayList<>();

}
