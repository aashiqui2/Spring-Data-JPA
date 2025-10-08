package com.springjpa.dto;

import com.springjpa.enums.Gender;
import com.springjpa.enums.PetType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// since it is a abstract class so we don't need @Builder
@NoArgsConstructor
@AllArgsConstructor // we need all args constructor because in child we have super(...);
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Setter
@Getter
public abstract class PetDTO {
	
	@EqualsAndHashCode.Include
	private int id;
	private String name;
	private Gender gender;
	private PetType type;
	private OwnerDTO ownerDTO;
}
