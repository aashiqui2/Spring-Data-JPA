package com.springjpa.mysql.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springjpa.mysql.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
	
	Optional<Owner> findByPetId(int petId);
	
}

