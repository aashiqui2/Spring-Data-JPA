package com.springjpa.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.springjpa.entity.Pet;
import com.springjpa.repository.PetRepository;

import jakarta.persistence.EntityManagerFactory;


@Repository
public class PetRepositoryImpl implements PetRepository {

	
	private EntityManagerFactory entityManagerFactory;

	@Override
	public Optional<Pet> findById(int petId) {
		throw new UnsupportedOperationException("Fetching pet by petId is not supported.");
	}

}
