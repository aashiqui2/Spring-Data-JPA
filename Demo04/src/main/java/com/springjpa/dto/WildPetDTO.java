package com.springjpa.dto;

import com.springjpa.enums.Gender;
import com.springjpa.enums.PetType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Builder //if we add @Builder its going to give only child field birthPlace
@ToString(callSuper = true)
@Setter
@Getter
public class WildPetDTO extends PetDTO {

	private String birthPlace;
	
	@Builder
	public WildPetDTO(int id,String name,Gender gender,PetType type,OwnerDTO ownerDTO,String birthPlace) {
		//super(id,name,gender,type,ownerDTO);
		this.birthPlace = birthPlace;
	}	
}
