package com.fernando.oliveira.traveler.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TravelerDTOTest {
	
	private static final String TRAVELER_NAME= "TRAVELER 04";
	private static final String TRAVELER_EMAIL= "traveler04@test.com";
	private static final String TRAVELER_DOCUMENT= "444.444.444.-44";
	
	private static final Integer PHONE_PREFIX = new Integer(11);
	private static final String PHONE_NUMBER = "95555-5555";
	
	@Test
	public void shouldBuild() {
		
		TravelerDTO dto = TravelerDTO.builder()
				.name(TRAVELER_NAME)
				.document(TRAVELER_DOCUMENT)
				.email(TRAVELER_EMAIL)
				.prefixPhone(PHONE_PREFIX)
				.numberPhone(PHONE_NUMBER)
				.build();
		Assertions.assertNotNull(dto);
		Assertions.assertEquals(dto.getName(), TRAVELER_NAME);
		Assertions.assertNotNull(dto.toString());
	}

}
