package com.springjpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springjpa.dto.OwnerDTO;
import com.springjpa.exception.OwnerNotFoundException;
import com.springjpa.exception.PetNotFoundException;
import com.springjpa.h2.entity.Pet;
import com.springjpa.h2.repository.PetRepository;
import com.springjpa.mysql.entity.Owner;
import com.springjpa.mysql.repository.OwnerRepository;
import com.springjpa.service.OwnerService;
import com.springjpa.util.OwnerMapper;
import com.springjpa.util.PetMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;
	private final PetRepository petRepository;
	private final OwnerMapper ownerMapper;
	private final PetMapper petMapper;

	@Value("${owner.not.found}")
	private String ownerNotFound;

	@Value("${pet.not.found}")
	private String petNotFound;

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		Pet pet = petMapper.petDTOToPet(ownerDTO.getPetDTO());
		Pet savedPet = petRepository.save(pet);
		Owner owner = ownerMapper.ownerDTOToOwner(ownerDTO);
		//OwnerDTO has PetDTO from converting DTO-> Entity Owner has pet_id so it is 0 
		owner.setPetId(savedPet.getId()); //to set the pet_id  we are getting from savedPet
		ownerRepository.save(owner);
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException, PetNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
		Pet pet = petRepository.findById(owner.getPetId())
				.orElseThrow(() -> new PetNotFoundException(String.format(petNotFound, owner.getPetId())));
		OwnerDTO ownerDTO = ownerMapper.ownerToOwnerDTO(owner);
		ownerDTO.setPetDTO(petMapper.petToPetDTO(pet));
		return ownerDTO;
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException, PetNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
		Pet pet = petRepository.findById(owner.getPetId())
				.orElseThrow(() -> new PetNotFoundException(String.format(petNotFound, ownerId)));
		pet.setName(petName);
		petRepository.save(pet);
	}
	
	@Transactional(transactionManager = "mysqlTransactionManager",rollbackFor = PetNotFoundException.class)
	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException, PetNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
		int petId=owner.getPetId();
		ownerRepository.deleteById(ownerId);
		boolean  petExists=petRepository.existsById(petId);
		if (!petExists) {
			throw new PetNotFoundException(String.format(petNotFound, ownerId));
		} else {
			petRepository.existsById(petId);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAll().stream().map(owner->{
			OwnerDTO ownerDTO=ownerMapper.ownerToOwnerDTO(owner);
			petRepository.findById(owner.getPetId())
			            .ifPresent(pet->ownerDTO.setPetDTO(petMapper.petToPetDTO(pet)));
			return ownerDTO;
		}).toList();
	}
}
