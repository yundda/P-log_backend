package com.example.plog.repository.pet;

import java.time.LocalDate;

import com.example.plog.repository.BaseEntity;
import com.example.plog.repository.Enum.Gender;

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
@Table(name = "pet_profile")
public class PetEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private Double weight;

    @Column(name = "photo")
    private String photo;

	public void setName(String name2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setName'");
	}

	public void setSpecies(String species2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setSpecies'");
	}

	public void setBreed(String breed2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setBreed'");
	}

	public void setBirthday(LocalDate birthday2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setBirthday'");
	}

	public void setGender(Gender gender2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setGender'");
	}

	public void setWeight(Double weight2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setWeight'");
	}

	public void setPhoto(String photo2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setPhoto'");
	}

}
