package com.fernando.oliveira.traveler.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fernando.oliveira.traveler.domain.Phone;
import com.fernando.oliveira.traveler.domain.Traveler;
import com.fernando.oliveira.traveler.dto.TravelerDTO;
import com.fernando.oliveira.traveler.model.PageModel;
import com.fernando.oliveira.traveler.model.PageRequestModel;
import com.fernando.oliveira.traveler.repository.TravelerRepository;
import com.fernando.oliveira.traveler.service.exception.TravelerInvalidException;
import com.fernando.oliveira.traveler.service.exception.TravelerNotFoundException;
import com.fernando.oliveira.traveler.service.impl.TravelerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TravelerServiceTest {

	private static final String TRAVELER_NAME = "traveler 01";
	private static final String TRAVELER_EMAIL = "traveler@test.com";
	
	private static final Integer TRAVELER_PHONE_PREFIX = 11;
	private static final String TRAVELER_PHONE_NUMBER = "988887777";
	
	private static final String TRAVELER_EMAIL_INVALID = "invalid.email.com";
	
	private static final String EMPTY = "";
	
	private static final String FIRST_PAGE = "0";
	private static final String PAGE_SIZE = "5";
	
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
	public void mustReturnExceptionMessageWhenTravelerHasPhoneWithoutPrefix() {
		Phone phone = buildPhone(null, TRAVELER_PHONE_NUMBER);
		Traveler traveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		
		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.save(traveler));
		Assertions.assertEquals("Telefone inválido", exception.getMessage());

	}
	
	@Test
	public void mustReturnExceptionMessageWhenTravelerHasPhoneWithoutNumber() {
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, null);
		Traveler traveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		
		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.save(traveler));
		Assertions.assertEquals("Telefone inválido", exception.getMessage());

	}
	
	@Test
	public void mustReturnExceptionMessageWhenTravelerHasPhoneWithEmptyNumber() {
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, "");
		Traveler traveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		
		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.save(traveler));
		Assertions.assertEquals("Telefone inválido", exception.getMessage());

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
	
	@Test
	public void shouldReturnTravelerById() {
		Long travelerId = new Long(1L);
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler savedTraveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		savedTraveler.setId(1L);
		Mockito.when(travelerRepository.findById(travelerId)).thenReturn(Optional.of(savedTraveler));
		
		Traveler result = travelerService.findById(travelerId);
		
		Assertions.assertEquals(result.getId(), 1L);
		
	}
	
	@Test 
	public void mustReturnExceptionMessageWhenTravelerNotFoundById() {
		
		Long id = 1001L;
		Mockito.when(travelerRepository.findById(id)).thenReturn(Optional.empty());
		
		Exception exception = Assertions.assertThrows(TravelerNotFoundException.class, () -> travelerService.findById(id));
		Assertions.assertEquals("Viajante não encontrado pelo id: " + id, exception.getMessage());
	}
	
	@Test
	public void shouldReturnEmptyListWhenDataNotFound() {
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("page", "0");
		params.put("size", "5");
		params.put("sort","");
		PageRequestModel pageRequestModel = new PageRequestModel(params);
		
		
		Pageable pageable = PageRequest.of(0, 5);
		Page<Traveler> page = new PageImpl<Traveler>(Arrays.asList(), pageable, Arrays.asList().size());
		
		String name = "XPTO";
		Mockito.when(travelerRepository.findByNameContainingOrderByNameAsc(name, pageable)).thenReturn(page);
		
		Exception exception = Assertions.assertThrows(TravelerNotFoundException.class, () -> travelerService.findByNameContainingOrderByNameAsc(name,pageRequestModel));
		Assertions.assertEquals("Não foram encontrados resultados" , exception.getMessage());
		
	}
	
	@Test
	public void shouldReturnExceptionMessageWhenTravelerNotFoundByName() {
		
		String param = "XPTO";
		Mockito.when(travelerRepository.findByName(param)).thenReturn(Optional.empty());
		
		Exception exception = Assertions.assertThrows(TravelerNotFoundException.class, () -> travelerService.findTravelerByName(param));
		Assertions.assertEquals("Não foram encontrados resultados" , exception.getMessage());
		
	}
	
	@Test
	public void shouldReturnListOfTravelers() {
		
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler savedTraveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		
		Mockito.when(travelerRepository.findAll()).thenReturn(Arrays.asList(savedTraveler));
		
		List<Traveler> result = travelerService.findAll();
		
		Assertions.assertFalse(result.isEmpty());
	}
	
	@Test
	public void mustReturnExceptionMessageWhenListTravelerHasEmpty() {
		
		Mockito.when(travelerRepository.findAll()).thenReturn(new ArrayList<>());
		
		Exception exception = Assertions.assertThrows(TravelerNotFoundException.class, () -> travelerService.findAll());
		Assertions.assertEquals("Não foram encontrados resultados" , exception.getMessage());
	}
	
	@Test
	public void shouldReturnTravelerListPageable() {

		Map<String,String> params = new HashMap<String, String>();
		params.put("page", FIRST_PAGE);
		params.put("size", PAGE_SIZE);
		params.put("sort","");
		PageRequestModel pageRequestModel = new PageRequestModel(params);
		
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler savedTraveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		Pageable pageable = PageRequest.of(0, 5);
		Page<Traveler> page = new PageImpl<Traveler>(Arrays.asList(savedTraveler), pageable, Arrays.asList(savedTraveler).size());
		
		Mockito.when(travelerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
		

		PageModel<TravelerDTO> result = travelerService.findAll(pageRequestModel);
		
		Assertions.assertEquals(result.getTotalElements(), 1);
		Assertions.assertNotNull(result.getElements());
		Assertions.assertEquals(result.getPageSize(), 5);
		Assertions.assertEquals(result.getElements().get(0).getName(), TRAVELER_NAME);
		
		
	}
	
	@Test
	public void shouldReturnTravelersByName() {
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("page", FIRST_PAGE);
		params.put("size", PAGE_SIZE);
		params.put("sort","");
		PageRequestModel pageRequestModel = new PageRequestModel(params);
		
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler savedTraveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		Pageable pageable = PageRequest.of(0, 5);
		Page<Traveler> page = new PageImpl<Traveler>(Arrays.asList(savedTraveler), pageable, Arrays.asList(savedTraveler).size());
		String name = "ELER";
		
		Mockito.when(travelerRepository.findByNameContainingOrderByNameAsc(name, pageable)).thenReturn(page);
		
		
		PageModel<TravelerDTO> result = travelerService.findByNameContainingOrderByNameAsc(name, pageRequestModel);
		
		
		Assertions.assertEquals(result.getTotalElements(), 1);
		Assertions.assertNotNull(result.getElements());
		Assertions.assertEquals(result.getPageSize(), 5);
		Assertions.assertEquals(result.getElements().get(0).getName(), TRAVELER_NAME);
		
		
	}
	
	@Test
	public void shouldUpdateTraveler() {

		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler travelerToUpdate = buildTraveler("NAME ALTERED", TRAVELER_EMAIL, phone);
		travelerToUpdate.setId(1L);
		
		Phone savedPhone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler savedTraveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, savedPhone);
		savedTraveler.setId(1L);
		
		Mockito.when(travelerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(savedTraveler));
	
		
		travelerService.update(travelerToUpdate);
		
		Assertions.assertEquals(travelerToUpdate.getName(), savedTraveler.getName());
		verify(travelerRepository).save(travelerToUpdate);
		
	}
	
	@Test
	public void shouldUpdateTravelerPhone() {

		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, "22222-2222");
		Traveler travelerToUpdate = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		travelerToUpdate.setId(1L);
		
		Phone savedPhone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler savedTraveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, savedPhone);
		savedTraveler.setId(1L);
		
		Mockito.when(travelerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(savedTraveler));
	
		
		travelerService.update(travelerToUpdate);
		
		Assertions.assertEquals(travelerToUpdate.getPhone().getNumber(), savedTraveler.getPhone().getNumber());
		verify(travelerRepository).save(travelerToUpdate);
		
	}
	
	
	@Test
	public void mustReturnExceptionMessageWhenUpdateTravelerWithPhoneNull() {

		
		Traveler travelerToUpdate = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, null);
		travelerToUpdate.setId(1L);
		
		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.update(travelerToUpdate));
		Assertions.assertEquals("Telefone é obrigatório" , exception.getMessage());
		
	}

	@Test
	public void mustReturnExceptionMessageWhenUpdateTravelerWithInvalidEmail() {

		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler travelerToUpdate = buildTraveler(TRAVELER_NAME, "email.invalid.com", phone);
		travelerToUpdate.setId(1L);
		
		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.update(travelerToUpdate));
		Assertions.assertEquals("Email inválido" , exception.getMessage());
		
	}
	
	@Test
	public void mustReturnExceptionMessageWhenUpdateTravelerWithNameThatExist() {

		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler travelerToUpdate = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		travelerToUpdate.setId(1L);
		
		Phone savedPhone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler savedTraveler = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, savedPhone);
		savedTraveler.setId(2L);
		
		Mockito.when(travelerRepository.findByName(TRAVELER_NAME)).thenReturn(Optional.of(savedTraveler));
		
		
		Exception exception = Assertions.assertThrows(TravelerInvalidException.class, () -> travelerService.update(travelerToUpdate));
		Assertions.assertEquals("Já existe viajante com o nome informado" , exception.getMessage());
		
	}
	
	@Test
	public void shouldDeleteTraveler() {
		
		Long id = new Long(1);
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler travelerToDelete = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		travelerToDelete.setId(id);
		Mockito.when(travelerRepository.findById(id)).thenReturn(Optional.of(travelerToDelete));
		
		travelerService.deleteById(id);
		
		verify(travelerRepository, times(1)).delete(travelerToDelete);
		
	}
	
	@Test
	public void mustReturnExceptionDeleteTravelerThatNonExist() {

		Long id = new Long(2);
		Phone phone = buildPhone(TRAVELER_PHONE_PREFIX, TRAVELER_PHONE_NUMBER);
		Traveler travelerToDelete = buildTraveler(TRAVELER_NAME, TRAVELER_EMAIL, phone);
		travelerToDelete.setId(id);
		Mockito.when(travelerRepository.findById(id)).thenReturn(Optional.empty());
		
		Exception exception = Assertions.assertThrows(TravelerNotFoundException.class, () -> travelerService.deleteById(id));
		Assertions.assertEquals("Viajante não encontrado pelo id: 2" , exception.getMessage());
		
	}
	

	
}
