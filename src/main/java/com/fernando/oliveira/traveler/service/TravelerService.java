package com.fernando.oliveira.traveler.service;

import java.util.List;

import com.fernando.oliveira.traveler.domain.Traveler;
import com.fernando.oliveira.traveler.dto.TravelerDTO;
import com.fernando.oliveira.traveler.model.PageModel;
import com.fernando.oliveira.traveler.model.PageRequestModel;

public interface TravelerService {
	
	public Traveler save(Traveler traveler);
	
	public Traveler update(Traveler traveler);
	
	public Traveler findById(Long id);
	
	public List<Traveler> findAll();
	
	public Traveler findTravelerByName(String name);
	
	public PageModel<TravelerDTO> findByNameContainingOrderByNameAsc(String name, PageRequestModel pageRequestModel);
	
	public PageModel<TravelerDTO> findAll(PageRequestModel pageRequestModel);


	
}
