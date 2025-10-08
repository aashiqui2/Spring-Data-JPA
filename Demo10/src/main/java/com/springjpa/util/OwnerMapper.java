package com.springjpa.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.springjpa.dto.OwnerDTO;
import com.springjpa.mysql.entity.Owner;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper {
	
	String UNSUPPORTED_PET_INSTANCE = "Unsupported pet instance: %s";

	@Mapping(source = "ownerDTO.petDTO.id", target = "petId")
	Owner ownerDTOToOwner(OwnerDTO ownerDTO);
	
	@Mapping(target="petDTO" ,ignore=true)
	OwnerDTO ownerToOwnerDTO(Owner owner);

	
	
}
