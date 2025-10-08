package com.springjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.springjpa.entity.Owner;
import com.springjpa.exception.OwnerNotFoundException;


public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    // By default, if you  put @Query, Spring Data generates a SELECT query for methods
	// Without @Modifying, Spring treats the query as a SELECT, so an UPDATE will throw an error
	// flushAutomatically = true pushes pending changes to the database before the query.
	// clearAutomatically = true detaches all entities from the persistence context after the query.
	// For UPDATE or DELETE, method name derivation(QueryBy Methods) doesn’t work; you must use @Modifying with @Query.
	
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Pet p SET p.name = :petName WHERE p.id = :petId")
    void updatePetDetails(int petId, String petName);

    
    @Transactional(rollbackFor = OwnerNotFoundException.class)
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Owner o WHERE o.id IN :ownerIds")
    void deleteByIds(List<Integer> ownerIds);
}
