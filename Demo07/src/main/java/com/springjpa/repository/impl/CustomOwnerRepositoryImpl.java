package com.springjpa.repository.impl;

import java.time.LocalDate;
import java.util.List;

import com.springjpa.entity.DomesticPet;
import com.springjpa.entity.Owner;
import com.springjpa.entity.Pet;
import com.springjpa.repository.CustomOwnerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

public class CustomOwnerRepositoryImpl implements CustomOwnerRepository {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public Owner findOwnerById(int ownerId) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

			// Tell Criteria API: this query will return Owner objects
			CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);

			// Equivalent to: FROM Owner o
			Root<Owner> root = criteriaQuery.from(Owner.class);

			// Eagerly load associated pets (similar to JOIN FETCH o.pet)
			root.fetch("pet");

			// Start with an empty WHERE clause (always true)
			Predicate where = criteriaBuilder.conjunction();

			// Add condition:
			// root.get("id") -> refers to the "id" column in Owner entity
			// criteriaBuilder.equal(...) -> builds "id = :ownerId"
			// criteriaBuilder.and(...) -> combines old conditions with this one
			// Final: WHERE o.id = :ownerId
			where = criteriaBuilder.and(where, criteriaBuilder.equal(root.get("id"), ownerId));

			criteriaQuery.select(root).where(where);

			// JPQL equivalent: SELECT o FROM Owner o JOIN FETCH o.pet WHERE o.id = :ownerId
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		}
	}

	@Override
	public List<Owner> findByFirstNameStartsWith(String firstName) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);

			// FROM Owner o
			Root<Owner> root = criteriaQuery.from(Owner.class);

			// Fetch associated pets
			root.fetch("pet");

			Predicate where = criteriaBuilder.conjunction();

			// Add condition:
			// root.get("firstName") -> points to Owner.firstName column
			// criteriaBuilder.like(...) -> builds "firstName LIKE :value%"
			// criteriaBuilder.and(...) -> merges with previous conditions
			// Final: WHERE o.firstName LIKE 'A%'
			where = criteriaBuilder.and(where, criteriaBuilder.like(root.get("firstName"), firstName + "%"));

			criteriaQuery.select(root).where(where);

			// JPQL: SELECT o FROM Owner o JOIN FETCH o.pet WHERE o.firstName LIKE
			// :firstName%
			return entityManager.createQuery(criteriaQuery).getResultList();
		}
	}

	@Override
	public Owner findOwnerByPetId(int petId) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);

			// FROM Owner o
			Root<Owner> root = criteriaQuery.from(Owner.class);

			// Fetch pets
			root.fetch("pet");

			// Join Owner with Pet (o JOIN o.pet p)
			Join<Owner, Pet> join = root.join("pet");

			Predicate where = criteriaBuilder.conjunction();

			// Add condition: p.id = :petId
			where = criteriaBuilder.and(where, criteriaBuilder.equal(join.get("id"), petId));

			criteriaQuery.select(root).where(where);

			// JPQL: SELECT o FROM Owner o JOIN o.pet p WHERE p.id = :petId
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		}
	}

	@Override
	public List<Owner> findAllOwnersByPetDateOfBirthRange(LocalDate startDate, LocalDate endDate) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Owner> criteriaQuery = criteriaBuilder.createQuery(Owner.class);

			// FROM Owner o
			Root<Owner> root = criteriaQuery.from(Owner.class);

			// Join Owner with DomesticPet (instead of abstract Pet)
			Join<Owner, DomesticPet> join = criteriaBuilder.treat(root.join("pet"), DomesticPet.class);

			Predicate where = criteriaBuilder.conjunction();

			// Add condition:
			// criteriaBuilder.between(...) -> builds "p.birthDate BETWEEN :startDate AND
			// :endDate"
			where = criteriaBuilder.and(where, criteriaBuilder.between(join.get("birthDate"), startDate, endDate));

			// Add ordering: ORDER BY p.birthDate ASC
			Order order = criteriaBuilder.asc(join.get("birthDate"));

			criteriaQuery.select(root).where(where).orderBy(order);

			// JPQL: SELECT o FROM Owner o JOIN o.pet p WHERE p.birthDate BETWEEN :startDate
			// AND :endDate ORDER BY p.birthDate ASC
			return entityManager.createQuery(criteriaQuery).getResultList();
		}
	}

	@Override
	public List<Object[]> findIdAndFirstNameAndLastNameAndPetName(int pageNumber, int pageSize) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

			// This query will return an Object[] (projection), not full Owner entity
			CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

			// FROM Owner o
			Root<Owner> root = criteriaQuery.from(Owner.class);

			// Join Owner -> Pet
			Join<Owner, Pet> join = root.join("pet");

			// Select specific columns instead of full entity
			// Equivalent to: SELECT o.id, o.firstName, o.lastName, p.name
			Selection<Object[]> selection = criteriaBuilder.array(root.get("id"), root.get("firstName"),
					root.get("lastName"), join.get("name"));

			criteriaQuery.select(selection);

			// Pagination setup
			// skipSize = number of records to skip = pageNumber * pageSize
			int skipSize = pageNumber * pageSize;

			// JPQL: SELECT o.id, o.firstName, o.lastName, p.name FROM Owner o JOIN o.pet p
			// LIMIT ? OFFSET ?
			return entityManager.createQuery(criteriaQuery).setMaxResults(pageSize) // page size (LIMIT)
					.setFirstResult(skipSize) // starting row (OFFSET)
					.getResultList();
		}
	}
}
