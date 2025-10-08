package com.springjpa.util;

import com.springjpa.dto.DomesticPetDTO;
import com.springjpa.dto.OwnerDTO;
import com.springjpa.dto.PetDTO;
import com.springjpa.dto.WildPetDTO;
import com.springjpa.entity.DomesticPet;
import com.springjpa.entity.Owner;
import com.springjpa.entity.Pet;
import com.springjpa.entity.WildPet;



public class PetMapperUtil {

	private static final String UNSUPPORTED_PET_INSTANCE = "Unsupported pet instance: %s";

	public static PetDTO petToPetDTO(Pet pet) {
		return switch (pet) {
		case DomesticPet domesticPet -> domesticPetToDomesticPetDTO(domesticPet);
		case WildPet wildPet -> wildPetToWildPetDTO(wildPet);
		default -> throw new IllegalArgumentException(String.format(UNSUPPORTED_PET_INSTANCE, pet.getClass()));
		};
	}

	private static DomesticPetDTO domesticPetToDomesticPetDTO(DomesticPet domesticPet) {
	    OwnerDTO ownerDTO = ownerToOwnerDTOWithoutPet(domesticPet.getOwner());

	    DomesticPetDTO domesticPetDTO = new DomesticPetDTO();
	    domesticPetDTO.setId(domesticPet.getId());
	    domesticPetDTO.setName(domesticPet.getName());
	    domesticPetDTO.setGender(domesticPet.getGender());
	    domesticPetDTO.setType(domesticPet.getType());
	    domesticPetDTO.setBirthDate(domesticPet.getBirthDate());
	    domesticPetDTO.setOwnerDTO(ownerDTO);
	    return domesticPetDTO;
	}

	private static WildPetDTO wildPetToWildPetDTO(WildPet wildPet) {
	    OwnerDTO ownerDTO = ownerToOwnerDTOWithoutPet(wildPet.getOwner());

	    WildPetDTO wildPetDTO = new WildPetDTO();
	    wildPetDTO.setId(wildPet.getId());
	    wildPetDTO.setName(wildPet.getName());
	    wildPetDTO.setGender(wildPet.getGender());
	    wildPetDTO.setType(wildPet.getType());
	    wildPetDTO.setBirthPlace(wildPet.getBirthPlace());
	    wildPetDTO.setOwnerDTO(ownerDTO);
	    return wildPetDTO;
	}

	private static OwnerDTO ownerToOwnerDTOWithoutPet(Owner owner) {
	    OwnerDTO ownerDTO = new OwnerDTO();
	    ownerDTO.setId(owner.getId());
	    ownerDTO.setFirstName(owner.getFirstName());
	    ownerDTO.setLastName(owner.getLastName());
	    ownerDTO.setGender(owner.getGender());
	    ownerDTO.setCity(owner.getCity());
	    ownerDTO.setState(owner.getState());
	    ownerDTO.setMobileNumber(owner.getMobileNumber());
	    ownerDTO.setEmailId(owner.getEmailId());
	    return ownerDTO;
	}

}
