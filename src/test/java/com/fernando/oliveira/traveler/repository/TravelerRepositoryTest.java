package com.fernando.oliveira.traveler.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fernando.oliveira.traveler.domain.Traveler;
import com.fernando.oliveira.traveler.repository.TravelerRepository;

@Sql(value="/load-db.sql", executionPhase=Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
@DataJpaTest
public class TravelerRepositoryTest {
	
	private static final String TRAVELER_NAME= "TRAVELER 01";
	
	@Autowired
	private TravelerRepository travelerRepository; 
	
	@Test
	public void shouldReturnTravelerByName() {
		
		Optional<Traveler> result = travelerRepository.findByName(TRAVELER_NAME);
		
		Assertions.assertThat(result.isPresent());
		
	}
	

}
