package com.springjpa.service.impl;

import java.util.List;
import java.util.Optional;

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
	
	
	private final  OwnerRepository ownerRepository;
	private final OwnerMapper ownerMapper;
	
	@Value("${owner.not.found}")
	private String ownerNotFound;
	

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		Owner owner = ownerMapper.ownerDTOToOwner(ownerDTO);
		ownerRepository.save(owner);
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		return ownerRepository.findById(ownerId)
				.map(ownerMapper::ownerToOwnerDTO)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		Optional<Owner> owner = ownerRepository.findById(ownerId);
		if (!owner.isPresent()) {
			throw new OwnerNotFoundException(String.format(ownerNotFound, ownerId));
		} else {
			Owner ownerEntity=owner.get();
			ownerEntity.getPet().setName(petName);
			ownerRepository.save(ownerEntity);
		}
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		Optional<Owner> owner = ownerRepository.findById(ownerId);
		if (!owner.isPresent()) {
			throw new OwnerNotFoundException(String.format(ownerNotFound, ownerId));
		} else {
			ownerRepository.deleteById(ownerId);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAll().stream().map(ownerMapper::ownerToOwnerDTO).toList();
	}

}
