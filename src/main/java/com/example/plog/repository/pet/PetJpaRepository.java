package com.example.plog.repository.pet;

<<<<<<< HEAD


import java.util.Optional;

=======
>>>>>>> ae26562a167dad86901b98b80ca98d2e7efb43a1
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PetJpaRepository extends JpaRepository<PetEntity, Long>{

<<<<<<< HEAD
    Boolean findByNameExists(String petName);

    Optional<PetEntity> findByName(String petName);
=======
    Boolean existsByName(String petName);
>>>>>>> ae26562a167dad86901b98b80ca98d2e7efb43a1
}
