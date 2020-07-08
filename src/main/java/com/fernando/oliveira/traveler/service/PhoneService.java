package com.fernando.oliveira.traveler.service;

import com.fernando.oliveira.traveler.domain.Phone;
import com.fernando.oliveira.traveler.dto.PhoneDTO;

public interface PhoneService {

	PhoneDTO convertObjectToDto(Phone phone);

	Phone convertDtoToObject(PhoneDTO phone);

	Phone save(Phone phone);

}
