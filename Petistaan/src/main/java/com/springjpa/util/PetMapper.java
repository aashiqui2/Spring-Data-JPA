package com.springjpa.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.springjpa.dto.DomesticPetDTO;
import com.springjpa.dto.PetDTO;
import com.springjpa.dto.WildPetDTO;
import com.springjpa.entity.DomesticPet;
import com.springjpa.entity.Pet;
import com.springjpa.entity.WildPet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapper {

	String UNSUPPORTED_PET_INSTANCE = "Unsupported pet instance: %s";
	
	default PetDTO petToPetDTO(Pet pet) {
		return switch (pet) {
		case DomesticPet domesticPet -> domesticPetToDomesticPetDTO(domesticPet);
		case WildPet wildPet -> wildPetToWildPetDTO(wildPet);
		default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, pet.getClass()));
		};
	}
	
	@Mapping(source="domesticPet.owner",target="ownerDTO")
	@Mapping(target="ownerDTO.petDTO", ignore=true)
	DomesticPetDTO domesticPetToDomesticPetDTO(DomesticPet domesticPet);
	
	@Mapping(source="wildPet.owner",target="ownerDTO")
	@Mapping(target="ownerDTO.petDTO", ignore=true)
	WildPetDTO wildPetToWildPetDTO(WildPet wildPet);
	
	

}
