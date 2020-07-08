package com.fernando.oliveira.traveler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fernando.oliveira.traveler.domain.Phone;
import com.fernando.oliveira.traveler.dto.PhoneDTO;
import com.fernando.oliveira.traveler.repository.PhoneRepository;
import com.fernando.oliveira.traveler.service.PhoneService;

@Service
public class PhoneServiceImpl implements PhoneService {

	@Autowired
	private PhoneRepository phoneRepository;
	
	@Override
	public Phone save(Phone phone) {
		return phoneRepository.save(phone);
		
	}
	
	@Override
	public PhoneDTO convertObjectToDto(Phone phone) {
		
		PhoneDTO phoneDTO = PhoneDTO.builder()
								.id(phone.getId())
								.prefix(phone.getPrefix())
								.number(phone.getNumber())
								.build();
		return phoneDTO;
	}

	@Override
	public Phone convertDtoToObject(PhoneDTO phoneDTO) {
		Phone phone = Phone.builder()
						.prefix(phoneDTO.getPrefix())
						.number(phoneDTO.getNumber())
						.build();
		return phone;
	}

	

}
