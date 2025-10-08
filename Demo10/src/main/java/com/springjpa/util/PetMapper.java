package com.springjpa.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.springjpa.dto.DomesticPetDTO;
import com.springjpa.dto.PetDTO;
import com.springjpa.dto.WildPetDTO;
import com.springjpa.h2.entity.DomesticPet;
import com.springjpa.h2.entity.Pet;
import com.springjpa.h2.entity.WildPet;

//if we add this this wil become bean add @Component at PetMapperImpl
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING) 
public interface PetMapper {
	
	String UNSUPPORTED_PET_INSTANCE = "Unsupported pet instance: %s";
	
	default Pet petDTOToPet(PetDTO petDTO) {
		return switch (petDTO) {
		case DomesticPetDTO domesticPetDTO -> domesticPetDTOToDomesticPet(domesticPetDTO);
		case WildPetDTO wildPetDTO -> wildPetDTOToWildPet(wildPetDTO);
		default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, petDTO.getClass()));
		};
	}

	DomesticPet domesticPetDTOToDomesticPet(DomesticPetDTO domesticPetDTO);

	WildPet wildPetDTOToWildPet(WildPetDTO wildPetDTO);
	
	default PetDTO petToPetDTO(Pet pet) {
		return switch (pet) {
		case DomesticPet domesticPet -> domesticPetToDomesticPetDTO(domesticPet);
		case WildPet wildPet -> wildPetToWildPetDTO(wildPet);
		default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, pet.getClass()));
		};
	}

	@Mapping(target = "ownerDTO", ignore = true)
	DomesticPetDTO domesticPetToDomesticPetDTO(DomesticPet domesticPet);

	@Mapping(target = "ownerDTO", ignore = true)
	WildPetDTO wildPetToWildPetDTO(WildPet wildPet);

}
