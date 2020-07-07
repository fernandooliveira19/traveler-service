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
import com.fernando.oliveira.traveler.dto.PhoneDTO;
import com.fernando.oliveira.traveler.dto.TravelerDTO;
import com.fernando.oliveira.traveler.repository.TravelerRepository;
import com.fernando.oliveira.traveler.service.PhoneService;
import com.fernando.oliveira.traveler.service.TravelerService;
import com.fernando.oliveira.traveler.service.exception.TravelerException;

@Service
public class TravelerServiceImpl implements TravelerService{

	@Autowired
	private TravelerRepository travelerRepository;
	
	@Autowired
	private PhoneService phoneService;
	
	public TravelerServiceImpl(TravelerRepository travelerRepository, PhoneService phoneService) {
		this.travelerRepository = travelerRepository;
		this.phoneService = phoneService;
	}
	
	@Override
	public TravelerDTO createTraveler(TravelerDTO dto) {
		
		Traveler traveler = convertDtoToObject(dto);
		
		Traveler travelerSaved = save(traveler);
		
		return convertObjectToDto(travelerSaved);
	}

	private TravelerDTO convertObjectToDto(Traveler traveler) {
		PhoneDTO phoneDTO = phoneService.convertObjectToDto(traveler.getPhone());
		TravelerDTO travelerDTO = TravelerDTO.builder()
										.id(traveler.getId())
										.name(traveler.getName())
										.email(traveler.getEmail())
										.document(traveler.getDocument())
										.phone(phoneDTO)
										.build();
		return travelerDTO;
	}

	private Traveler convertDtoToObject(TravelerDTO dto) {
		Phone phone = phoneService.convertDtoToObject(dto.getPhone());
		
		Traveler traveler = Traveler.builder()
								.name(dto.getName())
								.email(dto.getEmail())
								.document(dto.getDocument())
								.phone(phone)
								.build();
		return traveler;
	}

	@Transactional
	public Traveler save(Traveler traveler) {
		
		validate(traveler);
		
		Traveler travelerSaved = travelerRepository.save(traveler);
		
		Phone phone = traveler.getPhone();

		if (phone != null) {
			phone.setTraveler(travelerSaved);
			phoneService.save(phone);
		}

		return travelerSaved;
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
		String inv = "((.)*@(.)*@(.)*|(.)*[.][.](.)*|(.)*[.]@(.)*|(.)*@[.](.)*|^[.](.)*)";
		boolean invalido = Pattern.matches(inv, email);

		if (invalido) {
			throw new TravelerException("Email inválido");
		}

		String regValido = "^(.)+@[a-zA-Z0-9[-][.]]+[.]([a-zA-Z]{2,61}|[0-9]{1,3})";
		boolean valido = Pattern.matches(regValido, email);

		if (!valido) {
			throw new TravelerException("Email inválido");
		}

	}

	

	
	
}
