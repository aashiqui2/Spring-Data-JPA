package com.springjpa.repository.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.springjpa.entity.Owner;
import com.springjpa.entity.Pet;
import com.springjpa.repository.OwnerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;

@Repository
public class OwnerRepositoryImpl implements OwnerRepository {
    
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public void save(Owner owner) {
		try(EntityManager entityManager=entityManagerFactory.createEntityManager()){
			EntityTransaction entityTransaction=entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.persist(owner);
			entityTransaction.commit();	
		}	
	}

	@Override
	public Optional<Owner> findById(int ownerId) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			Owner owner = entityManager.find(Owner.class, ownerId);
			if (Objects.nonNull(owner)) {
			// since Pet is a abstract class we need the child class also, to fetch that use unproxy
				Pet pet = Hibernate.unproxy(owner.getPet(), Pet.class);
				owner.setPet(pet);
			}
			return Optional.ofNullable(owner);
		}
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			EntityTransaction entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Owner owner = entityManager.find(Owner.class, ownerId);
			if (Objects.nonNull(owner)) {
				owner.getPet().setName(petName);
			}
			entityTransaction.commit();
		}
	}

	@Override
	public void deleteById(int ownerId) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			EntityTransaction entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			Owner owner = entityManager.find(Owner.class, ownerId);
			if (Objects.nonNull(owner)) {
				entityManager.remove(owner);
			}
			entityTransaction.commit();
		}
	}

	@Override
	public List<Owner> findAll() {
		// without FETCH CAUSE N+1 problem
		String jpql = "SELECT o FROM Owner o JOIN FETCH o.pet";
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			return entityManager.createQuery(jpql, Owner.class)
			.getResultList();
		}
	}

}
