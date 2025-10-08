package com.springjpa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.springjpa.dto.OwnerDTO;
import com.springjpa.entity.Owner;
import com.springjpa.exception.OwnerNotFoundException;
import com.springjpa.repository.OwnerRepository;
import com.springjpa.repository.impl.OwnerRepositoryImpl;
import com.springjpa.service.OwnerService;
import com.springjpa.util.OwnerMapperUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {
	
	
	private final  OwnerRepository ownerRepository;
	
	// @Autowired No annotation needed If we use Constructor Injection	
	// public OwnerServiceImpl(OwnerRepository ownerRepository) {
	//	super();
	//	this.ownerRepository = ownerRepository;
	//}

	@Value("${owner.not.found}")
	private String ownerNotFound;
	
	public OwnerServiceImpl()
	{
		this.ownerRepository=new OwnerRepositoryImpl();

	}

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		Owner owner = OwnerMapperUtil.ownerDTOToOwner(ownerDTO);
		ownerRepository.save(owner);
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		return ownerRepository.findById(ownerId)
				.map(OwnerMapperUtil::ownerToOwnerDTO)
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
		return ownerRepository.findAll().stream().map(OwnerMapperUtil::ownerToOwnerDTO).toList();
	}

}
