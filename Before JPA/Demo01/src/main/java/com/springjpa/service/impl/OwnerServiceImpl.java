package com.springjpa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springjpa.dto.OwnerDTO;
import com.springjpa.entity.Owner;
import com.springjpa.exception.OwnerNotFoundException;
import com.springjpa.repository.OwnerRepository;
import com.springjpa.service.OwnerService;
import com.springjpa.util.MapperUtil;

@Service
public class OwnerServiceImpl implements OwnerService {
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	private String ownerNotFound;
	

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		Owner owner = MapperUtil.convertOwnerDtoToEntity(ownerDTO);
		ownerRepository.save(owner);
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		return ownerRepository.findById(ownerId)
				.map(MapperUtil::convertOwnerEntityToDto)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		if (optionalOwner.isEmpty()) {
			throw new OwnerNotFoundException(String.format(ownerNotFound, ownerId));
		} else {
			ownerRepository.updatePetDetails(ownerId, petName);
		}
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
		if (optionalOwner.isEmpty()) {
			throw new OwnerNotFoundException(String.format(ownerNotFound, ownerId));
		} else {
			ownerRepository.deleteById(ownerId);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAll().stream().map(MapperUtil::convertOwnerEntityToDto).toList();
	}

}
