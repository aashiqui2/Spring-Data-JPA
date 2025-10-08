package com.springjpa.service.impl.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.springjpa.dto.PetDTO;
import com.springjpa.entity.DomesticPet;
import com.springjpa.entity.Owner;
import com.springjpa.entity.Pet;
import com.springjpa.exception.PetNotFoundException;
import com.springjpa.repository.PetRepository;
import com.springjpa.service.impl.PetServiceImpl;
import com.springjpa.util.PetMapperImpl;




@TestPropertySource("classpath:messages.properties")
@SpringBootTest(classes = { PetServiceImpl.class, PetMapperImpl.class })
class PetServiceImplTest {

	@Autowired
	private PetServiceImpl petServiceImpl;
	@MockitoBean
	private PetRepository petRepository;

	@Test
	void test_FindPet_WhenPetExists_ShouldReturnPetDTO() throws PetNotFoundException {
		// Given
		int inputPetId = 1;
		Pet expectedDomesticPet = new DomesticPet();
		Owner expectedOwner = new Owner();
		expectedDomesticPet.setOwner(expectedOwner);
		when(petRepository.findById(inputPetId)).thenReturn(Optional.of(expectedDomesticPet));
		// When
		PetDTO actualPetDTO = petServiceImpl.findPet(inputPetId);
		// Then
		assertThat(actualPetDTO).isNotNull();
		verify(petRepository, times(1)).findById(inputPetId);
	}

	@Test
	void test_FindPet_WhenOwnerDoesNotExist_ShouldThrowPetNotFoundException() {
		// Given
		int inputPetId = 1;
		when(petRepository.findById(inputPetId)).thenReturn(Optional.empty());
		String expectedMessage = String.format("Can't find pet with petId %s", inputPetId);
		// When & Then
		assertThatThrownBy(() -> petServiceImpl.findPet(inputPetId)).isInstanceOf(PetNotFoundException.class)
				.hasMessage(expectedMessage);
		verify(petRepository, times(1)).findById(inputPetId);
	}

	@Test
	void test_FindAverageAgeOfPet_WhenPetsExists_ShouldReturnAverageAge() {
		// Given
		Double expectedAverageAge = 5.0;
		when(petRepository.findAverageAgeOfPet()).thenReturn(Optional.of(expectedAverageAge));
		// When
		Double actualAverageAge = petServiceImpl.findAverageAgeOfPet();
		// Then
		assertThat(actualAverageAge).isEqualTo(expectedAverageAge);
		verify(petRepository, times(1)).findAverageAgeOfPet();
	}

	@Test
	void test_FindAverageAgeOfPet_WhenPetsDoesNotExist_ShouldReturnZero() {
		// Given
		Double expectedAverageAge = 0.0;
		when(petRepository.findAverageAgeOfPet()).thenReturn(Optional.empty());
		// When
		Double actualAverageAge = petServiceImpl.findAverageAgeOfPet();
		// Then
		assertThat(actualAverageAge).isEqualTo(expectedAverageAge);
		verify(petRepository, times(1)).findAverageAgeOfPet();
	}

}
