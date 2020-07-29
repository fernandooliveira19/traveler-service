package com.fernando.oliveira.traveler.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fernando.oliveira.traveler.domain.Phone;
import com.fernando.oliveira.traveler.repository.PhoneRepository;
import com.fernando.oliveira.traveler.service.impl.PhoneServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PhoneServiceTest {
	
	private static final Long TRAVELER_PHONE_ID = new Long(1);
	private static final Integer TRAVELER_PHONE_PREFIX = 11;
	private static final String TRAVELER_PHONE_NUMBER = "988887777";

	@Mock
	private PhoneRepository phoneRepository;
	
	@Spy
	@InjectMocks
	private PhoneServiceImpl phoneService;
	
	@Test
	public void shouldCreatePhone() {
		Phone phoneToSave = Phone.builder().prefix(TRAVELER_PHONE_PREFIX).number(TRAVELER_PHONE_NUMBER).build();
		Phone savedPhone = Phone.builder().id(TRAVELER_PHONE_ID).prefix(TRAVELER_PHONE_PREFIX).number(TRAVELER_PHONE_NUMBER).build();
		Mockito.when(phoneRepository.save(phoneToSave)).thenReturn(savedPhone);
		
		
		Phone result = phoneService.save(phoneToSave);
		
		Assertions.assertNotNull(result.getId());
		verify(phoneRepository).save(phoneToSave);
	}

}
