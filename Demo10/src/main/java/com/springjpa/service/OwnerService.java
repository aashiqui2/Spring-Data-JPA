package com.springjpa.service;

import java.util.List;

import com.springjpa.dto.OwnerDTO;
import com.springjpa.exception.OwnerNotFoundException;
import com.springjpa.exception.PetNotFoundException;


public interface OwnerService {
	
	void saveOwner(OwnerDTO ownerDTO);

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException, PetNotFoundException;

	void updatePetDetails(int ownerId, String petName)  throws OwnerNotFoundException, PetNotFoundException;

	void deleteOwner(int ownerId)  throws OwnerNotFoundException, PetNotFoundException;

	List<OwnerDTO> findAllOwners();
	
}
