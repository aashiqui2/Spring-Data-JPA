package com.springjpa.dto;

import com.springjpa.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder // when we use @Builder pattern, it will create a parameterized constructor
@NoArgsConstructor //if we add this Builder won't provide no-args constructor
@AllArgsConstructor // so have @AllArgsConstructor so @Builder won't get angry.
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Setter
@Getter
public class OwnerDTO {
	
	@EqualsAndHashCode.Include
	private int id;
	private String firstName;
	private String lastName;
	private Gender gender;
	private String city;
	private String state;
	@EqualsAndHashCode.Include
	private String mobileNumber;
	@EqualsAndHashCode.Include
	private String emailId;
	private PetDTO petDTO;
}
