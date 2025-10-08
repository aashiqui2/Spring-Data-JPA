package com.springjpa.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.springjpa.dto.PetDTO;
import com.springjpa.exception.OwnerNotFoundException;
import com.springjpa.exception.PetNotFoundException;
import com.springjpa.h2.entity.Pet;
import com.springjpa.h2.repository.PetRepository;
import com.springjpa.mysql.entity.Owner;
import com.springjpa.mysql.repository.OwnerRepository;
import com.springjpa.service.PetService;
import com.springjpa.util.OwnerMapper;
import com.springjpa.util.PetMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {
	
	private final PetRepository petRepository;
	private final OwnerRepository ownerRepository;
	private final PetMapper petMapper;
	private final OwnerMapper ownerMapper;
	@Value("${pet.not.found}")
	private String petNotFound;
	@Value("${owner.pet.not.found}")
	private String ownerPetNotFound;

	@Override
	public PetDTO findPet(int petId) throws PetNotFoundException, OwnerNotFoundException {
		Pet pet = petRepository.findById(petId)
				.orElseThrow(() -> new PetNotFoundException(String.format(petNotFound, petId)));
		Owner owner = ownerRepository.findByPetId(petId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerPetNotFound, petId)));
		PetDTO petDTO = petMapper.petToPetDTO(pet);
		petDTO.setOwnerDTO(ownerMapper.ownerToOwnerDTO(owner));
		return petDTO;
	}
}
