package com.fernando.oliveira.traveler.resource;

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
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@Sql(value = "/load-db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clear-db.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
// @AutoConfigureMockMvc
public class TravelerControllerTest {

	private static final Integer PHONE_PREFIX = new Integer(11);
	private static final String PHONE_NUMBER = "95555-5555";
	
	private static final String TRAVELER_NAME= "TRAVELER 04";
	private static final String TRAVELER_EMAIL= "traveler04@test.com";
	private static final String TRAVELER_DOCUMENT= "444.444.444.-44";
	

	@Value("${server.port}")
	private int serverPort;

	@BeforeEach
	public void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = serverPort;
	}

	private Map<String, Object> buildTraveler(Map<String, Object> phone,String name, String email, String document) {
		Map<String, Object> traveler = new HashMap<String, Object>();
		traveler.put("name", name);
		traveler.put("email", email);
		traveler.put("document", document);
		traveler.put("phone", phone);
		return traveler;
	}

	private Map<String, Object> buildPhone(Integer prefix, String number) {
		Map<String, Object> phone = new HashMap<String, Object>();
		phone.put("prefix", prefix);
		phone.put("number", number);
		return phone;
	}

	
	@Test
	public void shouldCreateTraveler() {

		Map<String, Object> phone = buildPhone(PHONE_PREFIX, PHONE_NUMBER);

		Map<String, Object> traveler = buildTraveler(phone, TRAVELER_NAME, TRAVELER_EMAIL, TRAVELER_DOCUMENT);

		Response response = RestAssured.given().contentType("application/json").accept("application/json")
				.body(traveler).when().post("http://localhost:" + serverPort + "/api/travelers").then()
				.statusCode(HttpStatus.OK.value()).contentType("application/json").extract().response();
		String userId = response.jsonPath().getString("id");
		assertNotNull(userId);

	}

	@Test
	public void shouldReturnBadRequestCodeWhenTravelerNotValidated() {

		Map<String, Object> phone = buildPhone(PHONE_PREFIX, PHONE_NUMBER);

		Map<String, Object> traveler = buildTraveler(phone, null, TRAVELER_EMAIL, TRAVELER_DOCUMENT);

		Response response = RestAssured.given().contentType("application/json").accept("application/json")
				.body(traveler).when().post("http://localhost:" + serverPort + "/api/travelers")
				.then()
					.contentType("application/json").extract().response();
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
		
			
	}
	
}
