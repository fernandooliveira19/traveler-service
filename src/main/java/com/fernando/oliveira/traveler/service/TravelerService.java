package com.fernando.oliveira.traveler.service;

import java.util.List;

import com.fernando.oliveira.traveler.domain.Traveler;

public interface TravelerService {
	
	public Traveler save(Traveler traveler);
	
	public Traveler update(Traveler traveler);
	
	public Traveler findById(Long id);
	
	public List<Traveler> findAll();
	
	public Traveler findTravelerByName(String name);
	
	public List<Traveler> findByNameContainingOrderByNameAsc(String name);


	
}
