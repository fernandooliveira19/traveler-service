package com.fernando.oliveira.traveler.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fernando.oliveira.traveler.domain.Phone;
import com.fernando.oliveira.traveler.domain.Traveler;
import com.fernando.oliveira.traveler.repository.TravelerRepository;
import com.fernando.oliveira.traveler.service.PhoneService;
import com.fernando.oliveira.traveler.service.TravelerService;
import com.fernando.oliveira.traveler.service.exception.TravelerException;
import com.fernando.oliveira.traveler.service.exception.TravelerInvalidException;

@Service
public class TravelerServiceImpl implements TravelerService{

	@Autowired
	private TravelerRepository travelerRepository;
	
	@Autowired
	private PhoneService phoneService;
	
	@Transactional
	public Traveler save(Traveler traveler) {
		
		validate(traveler);
		
		Traveler createdTraveler = travelerRepository.save(traveler);
		
		createOrUpdatePhone(createdTraveler);

		return createdTraveler;
	}

	private void createOrUpdatePhone(Traveler traveler) {
		
		Phone phone = traveler.getPhone();
		phone.setTraveler(traveler);
		phoneService.save(phone);
		
	}
	
	public Traveler update(Traveler traveler) {
		
		validate(traveler);
		
		return travelerRepository.save(traveler);
	}
	
	@Override
	public Optional<Traveler> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Traveler> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Traveler> findTravelersByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void validate(Traveler traveler) {
		
		validateTravelerData(traveler);
		validateTravelerPhone(traveler);
		
	}
	
	private void validateTravelerData(Traveler traveler) {
		
		validateTravelerName(traveler);
		
		validateTravelerEmail(traveler);
		
		validateUniqueTraveler(traveler);
		
	}

	public void validateUniqueTraveler(Traveler traveler) {
		Optional<Traveler> travelerSaved = travelerRepository.findByName(traveler.getName());
		
		if(travelerSaved.isPresent()
				&& traveler.getId() == null) {
			throw new TravelerInvalidException("Já existe viajante com o nome informado");
		}
	}
	
	private void validateTravelerName(Traveler traveler) {
		if(StringUtils.isEmpty(traveler.getName())) {
			throw new TravelerInvalidException("Nome é obrigatório");
		}
	}
	
	private void validateTravelerEmail(Traveler traveler) {
		
		if(StringUtils.isEmpty(traveler.getEmail())) {
			throw new TravelerInvalidException("Email é obrigatório");
		}
		validateEmail(traveler.getEmail());
	}

	private void validateTravelerPhone(Traveler traveler) {
	
		if(traveler.getPhone() == null) {
			throw new TravelerInvalidException("Telefone é obrigatório");
		}
		
		if(traveler.getPhone().getPrefix() == null) {
			throw new TravelerInvalidException("Telefone inválido");
		}
		
		if(StringUtils.isEmpty(traveler.getPhone().getNumber())){
			throw new TravelerInvalidException("Telefone inválido");
		}
	}
	
	private void validateEmail(String email) {

		if (email == null) {
			throw new TravelerInvalidException("Email é obrigatorio");
		}
		String inv = "((.)*@(.)*@(.)*|(.)*[.][.](.)*|(.)*[.]@(.)*|(.)*@[.](.)*|^[.](.)*)";
		boolean invalido = Pattern.matches(inv, email);

		if (invalido) {
			throw new TravelerException("Email inválido");
		}

		String regValido = "^(.)+@[a-zA-Z0-9[-][.]]+[.]([a-zA-Z]{2,61}|[0-9]{1,3})";
		boolean valido = Pattern.matches(regValido, email);

		if (!valido) {
			throw new TravelerInvalidException("Email inválido");
		}

	}

	

	
	
}
