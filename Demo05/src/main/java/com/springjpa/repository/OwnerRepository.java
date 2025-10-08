package com.springjpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springjpa.entity.Owner;

//implementation of the CRUD from  SimpleJpaRepository<T, ID> 
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
	
	// Spring Data JPA does it at runtime, using proxy classes and method name parsing
	// Custom QueryBy method

	// SELECT o FROM Owner o WHERE o.id = :ownerId
	Owner findById(int ownerId);

	// SELECT o FROM Owner o WHERE o.firstName LIKE CONCAT(:firstName, '%')
	List<Owner> findByFirstNameStartsWith(String firstName); // both are same
	// List<Owner> findByFirstNameStartingWith(String firstName);

	// SELECT o FROM Owner o JOIN o.pets p WHERE p.id = :petId
	Optional<Owner> findByPet_Id(int petId);
}
