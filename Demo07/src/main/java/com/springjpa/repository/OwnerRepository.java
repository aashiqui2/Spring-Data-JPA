package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springjpa.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer>,CustomOwnerRepository {
	
	
}
