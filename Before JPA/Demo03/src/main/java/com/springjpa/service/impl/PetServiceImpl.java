package com.springjpa.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.springjpa.dto.PetDTO;
import com.springjpa.exception.PetNotFoundException;
import com.springjpa.repository.PetRepository;
import com.springjpa.repository.impl.PetRepositoryImpl;
import com.springjpa.service.PetService;
import com.springjpa.util.PetMapperUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {
	
	private final PetRepository petRepository;
	
	//@Autowired No annotation needed If we use Constructor Injection
	// public PetServiceImpl(PetRepository petRepository) {
	//	super();
	//	this.petRepository = petRepository;
	//}
		
	@Value("${pet.not.found}")
	private String petNotFound;
	
	public PetServiceImpl()
	{
		this.petRepository= new PetRepositoryImpl();
	}

	@Override
	public PetDTO findPet(int petId) throws PetNotFoundException {
		return petRepository.findById(petId)
				.map(PetMapperUtil::petToPetDTO)
				.orElseThrow(() -> new PetNotFoundException(String.format(petNotFound, petId)));
	}
}
