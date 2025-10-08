package com.springjpa.dto;

import java.time.LocalDate;

import com.springjpa.enums.Gender;
import com.springjpa.enums.PetType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Builder //if we add @Builder its going to give only child field birthPlace
@NoArgsConstructor
@ToString(callSuper = true)
@Setter
@Getter
public class DomesticPetDTO extends PetDTO {

	private LocalDate birthDate;
	
	@Builder
	public DomesticPetDTO(int id,String name,Gender gender,PetType type,OwnerDTO ownerDTO,LocalDate birthDate) {
		super(id,name,gender,type,ownerDTO);
		this.birthDate = birthDate;
	}	

}
