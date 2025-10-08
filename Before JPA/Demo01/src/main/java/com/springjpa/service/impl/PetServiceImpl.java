package com.springjpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.dto.PetDTO;
import com.springjpa.exception.PetNotFoundException;
import com.springjpa.repository.PetRepository;
import com.springjpa.repository.impl.PetRepositoryImpl;
import com.springjpa.service.PetService;
import com.springjpa.util.MapperUtil;

@Service
public class PetServiceImpl implements PetService {
	@Autowired
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
