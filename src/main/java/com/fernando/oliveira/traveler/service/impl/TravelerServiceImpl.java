package com.fernando.oliveira.traveler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fernando.oliveira.traveler.domain.Traveler;
import com.fernando.oliveira.traveler.exception.TravelerException;
import com.fernando.oliveira.traveler.repository.TravelerRepository;
import com.fernando.oliveira.traveler.service.TravelerService;

@Service
public class TravelerServiceImpl implements TravelerService{

	@Autowired
	private TravelerRepository travelerRepository;
	
	public TravelerServiceImpl(TravelerRepository travelerRepository) {
		this.travelerRepository = travelerRepository;
	}

	public Traveler save(Traveler traveler) {
		
		validate(traveler);
		
		return travelerRepository.save(traveler);
	}
	
	public Traveler update(Traveler traveler) {
		
		validate(traveler);
		
		return travelerRepository.save(traveler);
	}
	
	private void validate(Traveler traveler) {
		
		validateTravelerData(traveler);
		validateTravelerPhone(traveler);
		
	}
	
	private void validateTravelerData(Traveler traveler) {
		if(StringUtils.isEmpty(traveler.getName())) {
			throw new TravelerException("Nome é obrigatório");
		}
		
	}

	private void validateTravelerPhone(Traveler traveler) {
	
		if(traveler.getPhone() == null) {
			throw new TravelerException("Telefone é obrigatório");
		}
	}
	
}
