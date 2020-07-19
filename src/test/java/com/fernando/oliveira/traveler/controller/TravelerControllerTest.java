package com.fernando.oliveira.traveler.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TravelerControllerTest {

	private static final Integer PHONE_PREFIX = new Integer(11);
	private static final String PHONE_NUMBER = "95555-5555";
	
	private static final String TRAVELER_NAME= "TRAVELER 04";
	private static final String TRAVELER_EMAIL= "traveler04@test.com";
	private static final String TRAVELER_DOCUMENT= "444.444.444.-44";
	
	private static final String BASE_URI = "http://localhost:";
	private static final String CONTENT_TYPE = "application/json";
	

	@Value("${server.port}")
	private int serverPort;

	@BeforeEach
	public void setUp() throws Exception {
		RestAssured.baseURI = BASE_URI;
		RestAssured.port = serverPort;
	}

	private Map<String, Object> buildTravelerDTO(String name, String email, String document, Integer prefixPhone, String numberPhone) {
		Map<String, Object> travelerDTO = new HashMap<String, Object>();
		travelerDTO.put("name", name);
		travelerDTO.put("email", email);
		travelerDTO.put("document", document);
		travelerDTO.put("prefixPhone", prefixPhone);
		travelerDTO.put("numberPhone", numberPhone);
		return travelerDTO;
	}

	
	@Test
	public void shouldCreateTraveler() {

		
		Map<String, Object> travelerDTO = buildTravelerDTO(TRAVELER_NAME, TRAVELER_EMAIL, TRAVELER_DOCUMENT,PHONE_PREFIX, PHONE_NUMBER);

		Response response = RestAssured.given().contentType(CONTENT_TYPE).accept(CONTENT_TYPE)
				.body(travelerDTO).when().post(BASE_URI + serverPort + "/api/travelers").then()
				.statusCode(HttpStatus.CREATED.value()).contentType(CONTENT_TYPE).extract().response();
		String userId = response.jsonPath().getString("id");
		assertNotNull(userId);

	}

	@Test
	public void shouldReturnBadRequestCodeWhenTravelerIsInvalid() {

		Map<String, Object> travelerDTO = buildTravelerDTO(null, TRAVELER_EMAIL, TRAVELER_DOCUMENT,PHONE_PREFIX, PHONE_NUMBER);

		Response response = RestAssured.given().contentType(CONTENT_TYPE).accept(CONTENT_TYPE)
				.body(travelerDTO).when().post(BASE_URI + serverPort + "/api/travelers")
				.then()
					.contentType(CONTENT_TYPE).extract().response();
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		
			
	}
	
	@Test
	public void shouldReturnMessageWhenTravelerHaveNotName() {

		Map<String, Object> travelerDTO = buildTravelerDTO(null, TRAVELER_EMAIL, TRAVELER_DOCUMENT,PHONE_PREFIX, PHONE_NUMBER);

		Response response = RestAssured.given().contentType(CONTENT_TYPE).accept(CONTENT_TYPE)
				.body(travelerDTO).when().post(BASE_URI + serverPort + "/api/travelers")
				.then()
					.contentType(CONTENT_TYPE).extract().response();
		String message = response.jsonPath().getString("message");
		Assertions.assertEquals("Nome é obrigatório", message);
		
			
	}
	
}
