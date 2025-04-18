package com.example.plog.repository.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.plog.repository.BaseEntity;
import com.example.plog.repository.family.FamilyEntity;
import com.example.plog.repository.pet.PetEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "user")

public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable=false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_image")
    @Builder.Default
    private String profileImage = "profile1";

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<FamilyEntity> familyList = new ArrayList<>();

    @Transient
    public List<PetEntity> getPetList() {
    return familyList.stream()
                     .map(FamilyEntity::getPet)
                     .collect(Collectors.toList());
}

}
