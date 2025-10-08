package com.springjpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springjpa.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {

	@Query("SELECT o.id, o.firstName, o.lastName, o.pet.name FROM Owner o JOIN o.pet")
	List<Object[]> findIdAndFirstNameAndLastNameAndPetName(Pageable pageable);

}
