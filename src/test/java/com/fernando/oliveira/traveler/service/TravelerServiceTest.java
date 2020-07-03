package com.fernando.oliveira.traveler.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fernando.oliveira.traveler.domain.Phone;
import com.fernando.oliveira.traveler.domain.Traveler;
import com.fernando.oliveira.traveler.exception.TravelerException;
import com.fernando.oliveira.traveler.repository.TravelerRepository;
import com.fernando.oliveira.traveler.service.impl.TravelerServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TravelerServiceTest {

	private static String TRAVELER_NAME = "traveler 01";
	private static String TRAVELER_EMAIL = "traveler@test.com";
	
	private static Integer TRAVELER_PHONE_PREFIX = 11;
	private static String TRAVELER_PHONE_NUMBER = "988887777";
	

	@MockBean
	private TravelerRepository travelerRepository;

	@Autowired
	private TravelerServiceImpl travelerService;

	@Before
	public void setUp() {
		travelerService = new TravelerServiceImpl(travelerRepository);
	}

	@Test
	public void shouldCreateTraveler() {
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler traveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);

		travelerService.save(traveler);

		verify(travelerRepository).save(traveler);
	}

	private Traveler buildTraveler(String name, String email, Phone phone) {
		Traveler traveler = Traveler.builder().email(email).name(name).phone(phone).build();
		return traveler;
	}

	private Phone buildPhone(Integer prefix, String number) {
		Phone phone = Phone.builder().prefix(prefix).number(number).build();
		return phone;
	}

	@Test
	public void shouldNotSaveTravelerWithoutPhone() {

		Traveler traveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, null);
		
		Assertions.assertThrows(TravelerException.class, () -> travelerService.save(traveler),
				"Viajante deve possuir um telefone");

	}

	@Test
	public void shouldNotUpdateTravelerWithoutPhone() {
		Traveler traveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, null);
		
		Assertions.assertThrows(TravelerException.class, () -> travelerService.update(traveler),
				"Telefone é obrigatório");

	}

	@Test
	public void mustReturnExceptionMessageWhenTravelerHaventName() {
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler traveler = buildTraveler(null, TRAVELER_EMAIL, phone);
		
		Assertions.assertThrows(TravelerException.class, () -> travelerService.save(traveler),
				"Nome é obrigatório");
	}
	
	@Test
	public void mustReturnExceptionWhenExistsAnotherTravelerWithSameName() {
//		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
//		Traveler traveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
//		when(travelerRepository.findByName(TRAVELER_NAME)).thenReturn(Optional.of(traveler));
//		
//		 Assertions.assertThrows(TravelerException.class, () -> travelerService.save(traveler), "Já existe viajante com o nome informado");
	}

	@Test
	public void mustReturnExceptionMessageWhenTravelerHaventEmail() {

	}

	@Test
	public void mustReturnExceptionMessageWhenTravelerHaventPhone() {

	}

	@Test
	public void mustReturnExceptionMessageWhenTravelerHaveInvalidName() {

	}

	@Test
	public void mustReturnExceptionMessageWhenTravelerHaveInvalidEmail() {

	}

}
