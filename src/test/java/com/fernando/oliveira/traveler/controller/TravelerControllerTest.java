package com.fernando.oliveira.traveler.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.oliveira.traveler.domain.Traveler;
import com.fernando.oliveira.traveler.dto.TravelerDTO;
import com.fernando.oliveira.traveler.service.TravelerService;

@WebMvcTest(TravelerController.class)
@ActiveProfiles("test")
public class TravelerControllerTest {

	private static final Integer PHONE_PREFIX = new Integer(11);
	private static final String PHONE_NUMBER = "95555-5555";
	
	private static final String TRAVELER_NAME= "TRAVELER 04";
	private static final String TRAVELER_EMAIL= "traveler04@test.com";
	private static final String TRAVELER_DOCUMENT= "444.444.444.-44";
	
	
	private static final String REQUEST_MAPPING = "/api/travelers";
	private static final String ENCONDING = "UTF-8"; 
	

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockBean
	TravelerService travelerService;
	
	
	@Test
	public void shouldCreateTraveler() throws Exception{
		
		TravelerDTO dto = TravelerDTO.builder().name(TRAVELER_NAME).email(TRAVELER_EMAIL).document(TRAVELER_DOCUMENT).prefixPhone(PHONE_PREFIX).numberPhone(PHONE_NUMBER).build();
		Traveler traveler = dto.convertToTraveler();
		traveler.setId(1L);
		Mockito.when(travelerService.save(Mockito.any(Traveler.class))).thenReturn(traveler);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(REQUEST_MAPPING)
												.contentType(MediaType.APPLICATION_JSON)
												.accept(MediaType.APPLICATION_JSON)
												.characterEncoding(ENCONDING)
												.content(this.mapper.writeValueAsBytes(dto));
		
		mockMvc.perform(builder)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(traveler.convertToDTO())));
		
		
	}
	
	@Test
	public void shouldReturnTravelerById() throws Exception{
		
		TravelerDTO dto = TravelerDTO.builder().name(TRAVELER_NAME).email(TRAVELER_EMAIL).document(TRAVELER_DOCUMENT).prefixPhone(PHONE_PREFIX).numberPhone(PHONE_NUMBER).build();
		Traveler traveler = dto.convertToTraveler();
		traveler.setId(1L);
		Mockito.when(travelerService.findById(Mockito.anyLong())).thenReturn(traveler);
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(REQUEST_MAPPING +"/1")
												.contentType(MediaType.APPLICATION_JSON)
												.accept(MediaType.APPLICATION_JSON)
												.characterEncoding(ENCONDING);
//												.param("id", "1");
//												.content(this.mapper.writeValueAsBytes(dto));
		
		mockMvc.perform(builder)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(MockMvcResultMatchers.content().string(this.mapper.writeValueAsString(traveler.convertToDTO())));
		
		
	}
	
	
}
