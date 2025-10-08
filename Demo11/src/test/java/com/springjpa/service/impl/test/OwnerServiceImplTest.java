package com.springjpa.service.impl.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.springjpa.entity.DomesticPet;
import com.springjpa.entity.Owner;
import com.springjpa.entity.Pet;
import com.springjpa.exception.OwnerNotFoundException;
import com.springjpa.repository.OwnerRepository;
import com.springjpa.service.OwnerService;
import com.springjpa.service.impl.OwnerServiceImpl;



@TestPropertySource("classpath:messages.properties")
@SpringBootTest(classes = OwnerServiceImpl.class)
class OwnerServiceImplTest {
 
	@Autowired
	private OwnerService ownerService;
	
	@MockitoBean
	private OwnerRepository ownerRepository;

	@Test
	void test_UpdatePetDetails_WhenOwnerExists_ShouldUpdatePetDetails() throws OwnerNotFoundException {
		// Given
		int inputOwnerId = 1;
		String inputPetName = "NewPetName";
		Owner expectedOwner = new Owner();
		Pet expectedDomesticPet = new DomesticPet();
		expectedOwner.setPet(expectedDomesticPet);
		String expectedUpdatedPetName = inputPetName;
		
		when(ownerRepository.findById(inputOwnerId)).thenReturn(Optional.of(expectedOwner));
		// When
		ownerService.updatePetDetails(inputOwnerId, inputPetName);
		// Then
		assertThat(expectedOwner.getPet().getName()).isEqualTo(expectedUpdatedPetName);
		verify(ownerRepository, times(1)).findById(inputOwnerId);
		verify(ownerRepository, times(1)).save(expectedOwner);
	}

	@Test
	void test_UpdatePetDetails_WhenOwnerDoesNotExist_ShouldThrowOwnerNotFoundException() {
		// Given
		int inputOwnerId = 1;
		String inputPetName = "NewPetName";
		when(ownerRepository.findById(inputOwnerId)).thenReturn(Optional.empty());
		String expectedMessage = String.format("Can't find owner with ownerId %s", inputOwnerId);
		
		// When & Then
		assertThatThrownBy(() -> ownerService.updatePetDetails(inputOwnerId, inputPetName))
	    .isInstanceOf(OwnerNotFoundException.class)
	    .hasMessage(expectedMessage);
		verify(ownerRepository, times(1)).findById(inputOwnerId);
	}

	@Test
	void test_FindIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners_ShouldReturnOwnerDetails() {
		// Given
		int inputPageNumber = 1;
		int inputNumberOfRecordsPerPage = 2;
		Pageable pageable = PageRequest.of(inputPageNumber, inputNumberOfRecordsPerPage);
		List<Object[]> expectedDetailsList = List
				.<Object[]>of(new Object[] { 3, "FirstName3", "LastName3", "PetName3" });
		when(ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageable)).thenReturn(expectedDetailsList);
		// When
		List<Object[]> actualDetailsList = ownerService
				.findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(inputPageNumber, inputNumberOfRecordsPerPage);
		// Then
		assertThat(actualDetailsList).isEqualTo(expectedDetailsList);
		verify(ownerRepository, times(1)).findIdAndFirstNameAndLastNameAndPetName(pageable);
	}

}
