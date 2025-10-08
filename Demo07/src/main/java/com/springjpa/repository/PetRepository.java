package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springjpa.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer>, CustomPetRepository {

}
