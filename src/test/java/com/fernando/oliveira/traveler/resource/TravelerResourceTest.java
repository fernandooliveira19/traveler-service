package com.fernando.oliveira.traveler.resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

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

@Sql(value="/load-db.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value="/clear-db.sql", executionPhase=Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
//@AutoConfigureMockMvc
public class TravelerResourceTest {

	
	@Value("${server.port}")
	private int serverPort;

	@BeforeEach
	public void setUp() throws Exception{
		RestAssured.baseURI = "http://localhost";
		RestAssured.port=serverPort;
	}
	
	@Test
	public void shouldCreateTraveler() {
		Map<String, Object> traveler = new HashMap<String,Object>();
		traveler.put("name","NEW TRAVELER");
		traveler.put("email","new.traveler@test.com");
		traveler.put("document", "444.444.444-44");
		
		Map<String, Object> phone = new HashMap<String, Object>();
		phone.put("prefix", new Integer(11));
		phone.put("number", "95555-5555");
		
		traveler.put("phone", phone);
		
		Response response = RestAssured.given()
			.contentType("application/json")
			.accept("application/json")
			.body(traveler)
			.when()
				.post("http://localhost:"+serverPort+"/api/travelers")
			.then()
				.statusCode(HttpStatus.OK.value())
				.contentType("application/json")
				.extract()
				.response();
		String userId = response.jsonPath().getString("id");
		assertNotNull(userId);
		
	}

	
//	@Test
//	public void shouldReturnTravelerById() {
//		RestAssured.given()
//			.contentType("application/json")
//			.accept("application/json")
//			.pathParam("id", 1001)
//		.get("/api/travelers/{id}")	
//		.then()
//			.log().body().and()
//			.statusCode(HttpStatus.OK.value())
//			.body( "name", equalTo("TRAVELER 01"),
//					"email", equalTo("traveler01@test.com"));
//	}
}
