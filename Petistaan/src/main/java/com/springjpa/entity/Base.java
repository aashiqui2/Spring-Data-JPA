package com.springjpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class Base {
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;

}
