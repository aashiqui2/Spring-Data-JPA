package com.springjpa.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.springjpa.dto.DomesticPetDTO;
import com.springjpa.dto.OwnerDTO;
import com.springjpa.dto.PetDTO;
import com.springjpa.dto.WildPetDTO;
import com.springjpa.entity.DomesticPet;
import com.springjpa.entity.Owner;
import com.springjpa.entity.Pet;
import com.springjpa.entity.WildPet;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper {
	
	String UNSUPPORTED_PET_INSTANCE = "Unsupported pet instance: %s";

	@Mapping(source = "ownerDTO.petDTO", target = "pet")
	Owner ownerDTOToOwner(OwnerDTO ownerDTO);

	default Pet petDTOToPet(PetDTO petDTO) {
		return switch (petDTO) {
		case DomesticPetDTO domesticPetDTO -> domesticPetDTOToDomesticPet(domesticPetDTO);
		case WildPetDTO wildPetDTO -> wildPetDTOToWildPet(wildPetDTO);
		default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, petDTO.getClass()));
		};
	}

	@Mapping(target = "owner", ignore = true)
	DomesticPet domesticPetDTOToDomesticPet(DomesticPetDTO domesticPetDTO);

	@Mapping(target = "owner", ignore = true)
	WildPet wildPetDTOToWildPet(WildPetDTO wildPetDTO);
	
	@Mapping(source = "pet", target = "petDTO")
	OwnerDTO ownerToOwnerDTO(Owner owner);

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
