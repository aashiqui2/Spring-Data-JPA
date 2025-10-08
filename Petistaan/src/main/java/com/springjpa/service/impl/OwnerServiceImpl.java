package com.springjpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		Owner owner=ownerMapper.ownerDTOToOwner(ownerDTO);
		ownerRepository.save(owner);
	}
 
	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
	    return ownerRepository.findById(ownerId)
	            .map(owner -> ownerMapper.ownerToOwnerDTO(owner))
	            .orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
	}


	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		Owner owner=ownerRepository.findById(ownerId)
				.orElseThrow(()->new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
		owner.getPet().setName(petName);
		ownerRepository.save(owner);
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		boolean ownerExists=ownerRepository.existsById(ownerId);
		if(!ownerExists) {
			throw new OwnerNotFoundException(String.format(ownerNotFound, ownerId));
		}
		else {
			ownerRepository.deleteById(ownerId);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAll()
				.stream()
				.map(owner->ownerMapper.ownerToOwnerDTO(owner))
				.toList();
	}

	@Override
	public List<Object[]> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(int pageNumber,
			int numberOfRecordsPerPage) {
		Pageable pageable=PageRequest.of(pageNumber,numberOfRecordsPerPage);
		return ownerRepository.findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(pageable);	
	}

}
