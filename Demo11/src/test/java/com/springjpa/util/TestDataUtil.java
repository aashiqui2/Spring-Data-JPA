package com.springjpa.util;

import java.time.LocalDate;

import com.springjpa.entity.DomesticPet;
import com.springjpa.entity.Owner;
import com.springjpa.entity.Pet;
import com.springjpa.enums.Gender;
import com.springjpa.enums.PetType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestDataUtil {
	
	public static Owner createMockOwnerWithMockDomesticPet(String firstName, String lastName, Gender gender,
			String city, String state, String mobileNumber, String emailId, String petName, Gender petGender,
			PetType type, LocalDate birthDate) {
		Owner owner = createMockOwner(firstName, lastName, gender, city, state, mobileNumber, emailId);
		Pet domesticPet = createMockDomesticPet(petName, petGender, type, birthDate);
		owner.setPet(domesticPet);
		return owner;
	}

	public static Owner createMockOwner(String firstName, String lastName, Gender gender, String city, String state,
			String mobileNumber, String emailId) {
		Owner owner = new Owner();
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
		owner.setGender(gender);
		owner.setCity(city);
		owner.setState(state);
		owner.setMobileNumber(mobileNumber);
		owner.setEmailId(emailId);
		return owner;
	}

	public static DomesticPet createMockDomesticPet(String petName, Gender gender, PetType type, LocalDate birthDate) {
		DomesticPet domesticPet = new DomesticPet();
		domesticPet.setName(petName);
		domesticPet.setGender(gender);
		domesticPet.setType(type);
		domesticPet.setBirthDate(birthDate);
		return domesticPet;
	}
}
	

