package com.fernando.oliveira.traveler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fernando.oliveira.traveler.domain.Phone;
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
		

}
