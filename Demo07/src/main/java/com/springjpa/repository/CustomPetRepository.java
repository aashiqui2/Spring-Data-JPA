package com.springjpa.repository;

import com.springjpa.entity.Pet;

public interface CustomPetRepository {
	
	Pet findPetById(int petId);

	Double findAverageAgeOfPet();

}
