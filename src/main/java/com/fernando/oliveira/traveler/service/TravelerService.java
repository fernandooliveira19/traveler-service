package com.fernando.oliveira.traveler.service;

import java.util.List;
import java.util.Optional;

import com.fernando.oliveira.traveler.domain.Traveler;
import com.fernando.oliveira.traveler.dto.TravelerDTO;

public interface TravelerService {
	
	public Traveler save(Traveler traveler);
	
	public Traveler update(Traveler traveler);
	
	public Optional<Traveler> findById(Long id);
	
	public List<Traveler> findAll();
	
	public List<Traveler> findTravelersByName(String name);

	public TravelerDTO createTraveler(TravelerDTO dto);
	
	
}
