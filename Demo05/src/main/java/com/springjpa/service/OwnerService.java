package com.springjpa.service;

import java.util.List;

import com.springjpa.dto.OwnerDTO;
import com.springjpa.exception.OwnerNotFoundException;


public interface OwnerService {
	
	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

	List<OwnerDTO> findAllOwnersByFirstNameInitials(String firstName);

	OwnerDTO findOwnerByPetId(int petId) throws OwnerNotFoundException;
	
}
