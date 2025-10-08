package com.springjpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springjpa.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {

	Optional<Pet> findById(int petId);
	
}
