package com.springjpa.repository;

import java.time.LocalDate;
import java.util.List;

import com.springjpa.entity.Owner;

public interface CustomOwnerRepository {
	
	Owner findOwnerById(int ownerId);

	List<Owner> findByFirstNameStartsWith(String firstName);

	Owner findOwnerByPetId(int petId);

	List<Owner> findAllOwnersByPetDateOfBirthRange(LocalDate startDate, LocalDate endDate);

	List<Object[]> findIdAndFirstNameAndLastNameAndPetName(int pageNumber, int pageSize);

}
