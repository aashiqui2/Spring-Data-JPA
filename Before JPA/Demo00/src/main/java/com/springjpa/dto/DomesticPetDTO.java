package com.springjpa.dto;

import java.time.LocalDate;

public class DomesticPetDTO extends PetDTO {

	private LocalDate birthDate;

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

}
