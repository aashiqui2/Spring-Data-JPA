package com.springjpa.service;

import com.springjpa.dto.PetDTO;
import com.springjpa.exception.PetNotFoundException;


public interface PetService {
	
	PetDTO findPet(int petId) throws PetNotFoundException;
	
}
