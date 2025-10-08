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



@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pet_table")
@Entity
public abstract class Pet extends Base {

	@Column(name = "name", nullable = false)
	private String name;
	@Enumerated(value = EnumType.STRING)
	@Column(name = "gender", nullable = false)
	private Gender gender;
	@Enumerated(value = EnumType.STRING)
	@Column(name = "type", nullable = false)
	private PetType type;
	@OneToOne(mappedBy = "pet")
	private Owner owner;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public PetType getType() {
		return type;
	}
	public void setType(PetType type) {
		this.type = type;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	
}
