package com.springjpa.service;

import java.util.List;

import com.springjpa.dto.OwnerDTO;
import com.springjpa.exception.OwnerNotFoundException;


public interface OwnerService {

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

	void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

	void updatePetDetailsV2(int ownerId, String petName) throws OwnerNotFoundException;

	void deleteOwner(int ownerId) throws OwnerNotFoundException;

	void deleteOwners(List<Integer> ownerIds) throws OwnerNotFoundException;

	void deleteOwnersV2(List<Integer> ownerIds) throws OwnerNotFoundException;

}
 