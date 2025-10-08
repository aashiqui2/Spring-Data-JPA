package com.springjpa.service;

import java.util.List;

import com.springjpa.exception.OwnerNotFoundException;


public interface OwnerService {
	
	void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

	List<Object[]> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(int pageNumber, int numberOfRecordsPerPage);
	
}
