package com.springjpa.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springjpa.h2.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer>{
	
}
