package com.springjpa.service.impl;

import com.springjpa.dto.PetDTO;
import com.springjpa.exception.PetNotFoundException;
import com.springjpa.repository.PetRepository;
import com.springjpa.repository.impl.PetRepositoryImpl;
import com.springjpa.service.PetService;
import com.springjpa.util.MapperUtil;

public class PetServiceImpl implements PetService {

	private PetRepository petRepository;
	private String petNotFound;
	
	public PetServiceImpl()
	{
		this.petRepository= new PetRepositoryImpl();
	}

	@Override
	public PetDTO findPet(int petId) throws PetNotFoundException {
		return petRepository.findById(petId)
				.map(MapperUtil::convertPetEntityToDto)
				.orElseThrow(() -> new PetNotFoundException(String.format(petNotFound, petId)));
	}
}
