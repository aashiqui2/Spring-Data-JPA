package com.springjpa.service.impl;

import org.springframework.stereotype.Service;

import com.springjpa.repository.PetRepository;
import com.springjpa.service.PetService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {
	
	private final PetRepository petRepository;

	@Override
	public Double findAverageAgeOfPet() {
		return petRepository.findAverageAgeOfPet()
				.orElse(0.0);
	}
}
