package com.springjpa.service;

import java.util.List;

import com.springjpa.dto.OwnerDTO;
import com.springjpa.exception.OwnerNotFoundException;


public interface OwnerService {
	
	void saveOwner(OwnerDTO ownerDTO);

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

	void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

	void deleteOwner(int ownerId) throws OwnerNotFoundException;

	List<OwnerDTO> findAllOwners();
	
}
