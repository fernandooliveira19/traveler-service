package com.fernando.oliveira.traveler.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fernando.oliveira.traveler.domain.Phone;
import com.fernando.oliveira.traveler.domain.Traveler;
import com.fernando.oliveira.traveler.dto.TravelerDTO;
import com.fernando.oliveira.traveler.model.PageModel;
import com.fernando.oliveira.traveler.model.PageRequestModel;
import com.fernando.oliveira.traveler.repository.TravelerRepository;
import com.fernando.oliveira.traveler.service.PhoneService;
import com.fernando.oliveira.traveler.service.TravelerService;
import com.fernando.oliveira.traveler.service.exception.TravelerException;
import com.fernando.oliveira.traveler.service.exception.TravelerInvalidException;
import com.fernando.oliveira.traveler.service.exception.TravelerNotFoundException;

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
		
		Traveler savedTraveler = findById(traveler.getId());
		
		savedTraveler.setName(traveler.getName());
		savedTraveler.setEmail(traveler.getEmail());
		savedTraveler.setDocument(traveler.getDocument());
		savedTraveler.getPhone().setPrefix(traveler.getPhone().getPrefix());
		savedTraveler.getPhone().setNumber(traveler.getPhone().getNumber());
		
		return travelerRepository.save(savedTraveler);
	}
	
	@Override
	public Traveler findById(Long id) {
		
		Optional<Traveler> result = travelerRepository.findById(id);
		
		return result.orElseThrow(() -> new TravelerNotFoundException("Viajante não encontrado pelo id: " + id) );
	}

	@Override
	public List<Traveler> findAll() {
		List<Traveler> result = travelerRepository.findAll();
		if(result.isEmpty()) {
			throw new TravelerNotFoundException("Não foram encontrados resultados");
		}
		return result;
	}
	
	@Override
	public Traveler findTravelerByName(String name) {
		Optional<Traveler> result = travelerRepository.findByName(name);
		return result.orElseThrow(() -> new TravelerNotFoundException("Não foram encontrados resultados"));
	}
	
	@Override
	public PageModel<TravelerDTO> findAll(PageRequestModel pageRequestModel) {
		
		Pageable pageable = pageRequestModel.toSpringPageRequest();
		
		Page<Traveler> page = travelerRepository.findAll(pageable);
		
		List<TravelerDTO> collect = page.getContent()
		.stream()
		.map((e) -> e.convertToDTO())
		.collect(Collectors.toList());
		
		PageModel<TravelerDTO> pageModel = new PageModel<TravelerDTO>((int)page.getTotalElements(), page.getSize(),page.getTotalPages(), collect);
		
		return pageModel;
	}

	
	@Override
	public PageModel<TravelerDTO> findByNameContainingOrderByNameAsc(String name, PageRequestModel pageRequestModel) {
		Pageable pageable = pageRequestModel.toSpringPageRequest();
		
		Page<Traveler> page = travelerRepository.findByNameContainingOrderByNameAsc(name, pageable);
		
		List<TravelerDTO> collect = page.getContent()
				.stream()
				.map((e) -> e.convertToDTO())
				.collect(Collectors.toList());
		
		if(collect.isEmpty()) {
			throw new TravelerNotFoundException("Não foram encontrados resultados");
		}
		
		PageModel<TravelerDTO> pageModel = new PageModel<TravelerDTO>((int)page.getTotalElements(), page.getSize(),page.getTotalPages(), collect);
		
		return pageModel;
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
		Optional<Traveler> savedTraveler = travelerRepository.findByName(traveler.getName());
		
		if(savedTraveler.isPresent()
				&& (traveler.getId() == null 
					|| !traveler.getId().equals(savedTraveler.get().getId()))) {
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
