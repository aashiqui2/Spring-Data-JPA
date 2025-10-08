package com.springjpa.service;

import java.time.LocalDate;
import java.util.List;

import com.springjpa.dto.OwnerDTO;
import com.springjpa.exception.OwnerNotFoundException;


public interface OwnerService {
	
	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

	List<OwnerDTO> findAllOwnersByFirstNameInitials(String firstName);

	OwnerDTO findOwnerByPetId(int petId) throws OwnerNotFoundException;

	List<OwnerDTO> findByAllOwnersByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate);
	
	List<Object[]> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(int pageNumber, int numberOfRecordsPerPage);
	
}
 