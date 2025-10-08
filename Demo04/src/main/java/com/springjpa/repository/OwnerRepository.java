package com.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springjpa.entity.Owner;

//implementation of the CRUD from  SimpleJpaRepository<T, ID> 
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
	
}

