package com.fernando.oliveira.traveler.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fernando.oliveira.traveler.domain.Phone;
import com.fernando.oliveira.traveler.domain.Traveler;
import com.fernando.oliveira.traveler.repository.TravelerRepository;
import com.fernando.oliveira.traveler.service.exception.TravelerInvalidException;
import com.fernando.oliveira.traveler.service.impl.TravelerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TravelerServiceTest {

	private static final String TRAVELER_NAME = "traveler 01";
	private static final String TRAVELER_EMAIL = "traveler@test.com";
	
	private static final Integer TRAVELER_PHONE_PREFIX = 11;
	private static final String TRAVELER_PHONE_NUMBER = "988887777";
	
	private static final String TRAVELER_EMAIL_INVALID = "invalid.email.com";
	
	private static final String EMPTY = "";
	
	@Mock
	private TravelerRepository travelerRepository;
	
	@Mock
	private PhoneService phoneService;

	@Spy
	@InjectMocks
	private TravelerServiceImpl travelerService;
	

	private Traveler buildTraveler(String name, String email, Phone phone) {
		Traveler traveler = Traveler.builder().email(email).name(name).phone(phone).build();
		return traveler;
	}

	private Phone buildPhone(Integer prefix, String number) {
		Phone phone = Phone.builder().prefix(prefix).number(number).build();
		return phone;
	}

	@Test
	public void shouldCreateTraveler() {

		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler travelerToSave = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		Traveler savedTraveler = travelerToSave;
		savedTraveler.setId(1L);
		Mockito.when(travelerRepository.save(travelerToSave)).thenReturn(savedTraveler);
		
		savedTraveler = travelerService.save(travelerToSave);
		
		Assertions.assertNotNull(savedTraveler.getId());
		verify(travelerRepository).save(travelerToSave);
		
	}

	@Test
	@Disabled
	public void shouldCreateTravelerWithPhone() {

		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler travelerToSave = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		Traveler savedTraveler = travelerToSave;
		savedTraveler.setId(2L);
		Mockito.when(travelerRepository.save(travelerToSave)).thenReturn(savedTraveler);
		Mockito.when(travelerRepository.findByName(TRAVELER_NAME)).thenReturn(Optional.empty());
		
		savedTraveler = travelerService.save(travelerToSave);
		
		Assertions.assertNotNull(savedTraveler.getPhone().getTraveler());
		verify(phoneService).save(travelerToSave.getPhone());
		
		
	}


	@Test
	public void mustReturnExceptionMessageWhenTravelerHasNoPhone() {
		Traveler traveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, null);
		
		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.save(traveler));
		Assertions.assertEquals("Telefone é obrigatório", exception.getMessage());

	}

	@Test
	public void mustReturnExceptionMessageWhenTravelerHasNameNull() {
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler traveler = buildTraveler(null, TRAVELER_EMAIL, phone);
		
		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.save(traveler));
		Assertions.assertEquals("Nome é obrigatório", exception.getMessage());
	}
	
	@Test
	public void mustReturnExceptionMessageWhenTravelerHasNameEmpty() {
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler traveler = buildTraveler(EMPTY, TRAVELER_EMAIL, phone);
		

		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.save(traveler));
		Assertions.assertEquals("Nome é obrigatório", exception.getMessage());
	}
	
	@Test
	public void mustReturnExceptionWhenExistsAnotherTravelerWithSameName() {
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler traveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		when(travelerRepository.findByName(TRAVELER_NAME)).thenReturn(Optional.of(traveler));
		
		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.save(traveler));
		Assertions.assertEquals("Já existe viajante com o nome informado", exception.getMessage());
	}

	@Test
	public void mustReturnExceptionMessageWhenTravelerHasEmailNull() {

		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler traveler = buildTraveler(TRAVELER_NAME, null, phone);
		
		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.save(traveler));
		Assertions.assertEquals("Email é obrigatório", exception.getMessage());
		
	}
	
	@Test
	public void mustReturnExceptionMessageWhenTravelerHasEmailEmpty() {
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler traveler = buildTraveler(TRAVELER_NAME, EMPTY, phone);
		
		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.save(traveler));
		Assertions.assertEquals("Email é obrigatório", exception.getMessage());
		
	}
	
	@Test
	public void mustReturnExceptionMessageWhenTravelerHasEmailInvalid() {
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler traveler = buildTraveler(TRAVELER_NAME,TRAVELER_EMAIL_INVALID , phone);
		
		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.save(traveler));
		Assertions.assertEquals("Email inválido", exception.getMessage());
		
	}
	
}
