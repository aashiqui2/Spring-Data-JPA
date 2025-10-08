package com.springjpa.repository;

import java.util.Optional;

import com.springjpa.entity.Pet;


public interface PetRepository {
	
	Optional<Pet> findById(int petId);
	
}
