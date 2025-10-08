package com.springjpa.repository.impl;

import java.time.LocalDate;

import com.springjpa.entity.DomesticPet;
import com.springjpa.entity.Pet;
import com.springjpa.repository.CustomPetRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

public class CustomPetRepositoryImpl implements CustomPetRepository {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public Pet findPetById(int petId) {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

			CriteriaQuery<Pet> criteriaQuery = criteriaBuilder.createQuery(Pet.class);
			Root<Pet> root = criteriaQuery.from(Pet.class);

			Predicate where = criteriaBuilder.conjunction();

			where = criteriaBuilder.and(where, criteriaBuilder.equal(root.get("id"), petId));
			criteriaQuery.select(root).where(where);

			// SELECT p FROM Pet p WHERE p.id=:petId
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		}
	}

	@Override
	public Double findAverageAgeOfPet() {
		try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
		    // 1. Create CriteriaBuilder (entry point for Criteria API)
		    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		    
		    // 2. Define query type -> it will return a Double (average age)
		    CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
		    
		    // 3. Define FROM clause: FROM Pet p
		    Root<DomesticPet> root = criteriaQuery.from(DomesticPet.class);

		    // 4. Build selection (what to SELECT):
		    //    - criteriaBuilder.function("year", ...) → YEAR(p.birthDate)
		    //    - LocalDate.now().getYear()             → current year (e.g., 2025)
		    //    - diff(...) → currentYear - birthYear  → approximate age
		    //    - avg(...) → average across all pets
		    Selection<Double> selection = criteriaBuilder.avg(
		        criteriaBuilder.diff(
		            LocalDate.now().getYear(),
		            criteriaBuilder.function("year", Integer.class, root.get("birthDate"))
		        )
		    );

		    // 5. Put selection into the CriteriaQuery
		    criteriaQuery.select(selection);

		    // 6. Execute query:
		    //   SELECT AVG(YEAR(CURRENT_DATE) - YEAR(p.birthDate)) FROM Pet p
		    return entityManager.createQuery(criteriaQuery).getSingleResult();
		}
	}

}
