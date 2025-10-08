package com.springjpa.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.springjpa.dto.OwnerDTO;
import com.springjpa.entity.Owner;
import com.springjpa.exception.OwnerNotFoundException;
import com.springjpa.repository.OwnerRepository;
import com.springjpa.service.OwnerService;
import com.springjpa.util.OwnerMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;
	private final OwnerMapper ownerMapper;
	
	@Value("${owner.not.found}")
	private String ownerNotFound;
	
	@Value("${owner.pet.not.found}")
	private String ownerPetNotFound;

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(ownerId);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(ownerNotFound, ownerId));
		} else {
			return ownerMapper.ownerToOwnerDTO(owner);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwnersByFirstNameInitials(String firstName) {
		return ownerRepository.findByFirstNameStartsWith(firstName)
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}

	@Override
	public OwnerDTO findOwnerByPetId(int petId) throws OwnerNotFoundException {
		return ownerRepository.findByPet_Id(petId)
				.map(ownerMapper::ownerToOwnerDTO)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerPetNotFound, petId)));
	}


}

