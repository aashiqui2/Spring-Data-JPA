package com.springjpa.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.springjpa.dto.PetDTO;
import com.springjpa.entity.Pet;
import com.springjpa.exception.PetNotFoundException;
import com.springjpa.repository.PetRepository;
import com.springjpa.service.PetService;
import com.springjpa.util.PetMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {

	private final PetRepository petRepository;
	private final PetMapper petMapper;
	
	@Value("${pet.not.found}")
	private String petNotFound;

	@Override
	public PetDTO findPet(int petId) throws PetNotFoundException {
		Pet pet = petRepository.findPetById(petId);
		if (Objects.isNull(pet)) {
			throw new PetNotFoundException(String.format(petNotFound, petId));
		} else {
			return petMapper.petToPetDTO(pet);
		}
	}

	@Override
	public Double findAverageAgeOfPet() {
		Double averageAge = petRepository.findAverageAgeOfPet();
		if (Objects.isNull(averageAge)) {
			averageAge = 0.0;
		}
		return averageAge;
	}


}