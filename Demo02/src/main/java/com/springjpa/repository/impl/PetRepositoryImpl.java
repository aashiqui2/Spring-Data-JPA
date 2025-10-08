package com.springjpa.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.springjpa.entity.Pet;
import com.springjpa.repository.PetRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;


@Repository
public class PetRepositoryImpl implements PetRepository {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public Optional<Pet> findById(int petId) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			Pet pet = entityManager.find(Pet.class, petId);
			return Optional.ofNullable(pet);
		}
	}

}
