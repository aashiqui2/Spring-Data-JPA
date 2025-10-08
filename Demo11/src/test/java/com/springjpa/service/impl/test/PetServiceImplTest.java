package com.springjpa.service.impl.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.springjpa.repository.PetRepository;
import com.springjpa.service.PetService;
import com.springjpa.service.impl.PetServiceImpl;

@SpringBootTest(classes = PetServiceImpl.class)
class PetServiceImplTest {
	
	@Autowired
	private PetService petService;
	
	@MockitoBean
	private PetRepository petRepository;
	
	@Test
	void test_findAverageAgeOfPet_WhenPetExists_ShouldReturnAverageAge() {
		// Given 
		Double expectedAverageAge=5.0;
		Mockito.when(petRepository.findAverageAgeOfPet()).thenReturn(Optional.of(expectedAverageAge));
		// When
		Double actualAverageAge=petService.findAverageAgeOfPet();
		// Then 
		assertThat(actualAverageAge).isEqualTo(expectedAverageAge);
		Mockito.verify(petRepository,Mockito.times(1)).findAverageAgeOfPet();
		
	} 
	
	@Test
	void test_FindAverageAgeOfPet_WhenPetsDoesNotExist_ShouldReturnZero() {
		// Given
		Double expectedAverageAge = 0.0;
		Mockito.when(petRepository.findAverageAgeOfPet()).thenReturn(Optional.empty());
		// When
		Double actualAverageAge = petService.findAverageAgeOfPet();
		// Then
		assertThat(actualAverageAge).isCloseTo(expectedAverageAge,Assertions.withPrecision(0.1));
		Mockito.verify(petRepository,Mockito. times(1)).findAverageAgeOfPet();
	}

}
