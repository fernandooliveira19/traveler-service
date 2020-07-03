package com.fernando.oliveira.traveler.service.impl;

import java.util.Optional;
import java.util.regex.Pattern;

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
		
		validateTravelerName(traveler);
		
		validateTravelerEmail(traveler);
		
		validateUniqueTraveler(traveler);
		
	}

	private void validateUniqueTraveler(Traveler traveler) {
		Optional<Traveler> travelerSaved = travelerRepository.findByName(traveler.getName());
		
		if(travelerSaved.isPresent()
				&& traveler.getId() == null) {
			throw new TravelerException("Já existe viajante com o nome informado");
		}
	}
	
	private void validateTravelerName(Traveler traveler) {
		if(StringUtils.isEmpty(traveler.getName())) {
			throw new TravelerException("Nome é obrigatório");
		}
	}
	
	private void validateTravelerEmail(Traveler traveler) {
		
		if(StringUtils.isEmpty(traveler.getEmail())) {
			throw new TravelerException("Email é obrigatório");
		}
		validateEmail(traveler.getEmail());
	}

	private void validateTravelerPhone(Traveler traveler) {
	
		if(traveler.getPhone() == null) {
			throw new TravelerException("Telefone é obrigatório");
		}
	}
	
	private void validateEmail(String email) {

		if (email == null) {
			throw new TravelerException("Email é obrigatorio");
		}
		// System.out.print(email +" = ");
		// expressões regex para e-mail invalido
		// (.)*@(.)*@(.)* não pode ter mais de um @
		// (.)*[.][.](.)* não pode ter ponto seguido ..
		// (.)*[.]@(.)* não pode ter @.
		// (.)*@[.](.)* não pode ter .@
		// ^[.](.)* não pode começar com .
		String inv = "((.)*@(.)*@(.)*|(.)*[.][.](.)*|(.)*[.]@(.)*|(.)*@[.](.)*|^[.](.)*)";
		boolean invalido = Pattern.matches(inv, email);

		if (invalido) {
			throw new TravelerException("Email inválido");
		}

		// expressão regex para e-mail valido
		String regValido = "^(.)+@[a-zA-Z0-9[-][.]]+[.]([a-zA-Z]{2,61}|[0-9]{1,3})";
		boolean valido = Pattern.matches(regValido, email);

		if (!valido) {
			throw new TravelerException("Email inválido");
		}

	}
	
}
