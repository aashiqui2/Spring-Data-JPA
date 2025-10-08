package com.springjpa.entity;

import com.springjpa.enums.Gender;
import com.springjpa.enums.PetType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name="pet_table")
public abstract class Pet extends Base{
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="gender",nullable = false)
	@Enumerated(value=EnumType.STRING)
	private Gender gender;
	
	@Enumerated(value=EnumType.STRING)
	@Column(name="type",nullable = false)
	private PetType type;

	@OneToOne(mappedBy = "pet")
	private Owner owner;
}
