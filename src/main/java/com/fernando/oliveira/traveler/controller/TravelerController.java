package com.fernando.oliveira.traveler.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.oliveira.traveler.domain.Traveler;
import com.fernando.oliveira.traveler.dto.TravelerDTO;
import com.fernando.oliveira.traveler.service.TravelerService;

@RestController
@RequestMapping("/api/travelers")
public class TravelerController {
	
	@Autowired
	private TravelerService travelerService;
	
	@PostMapping
	public ResponseEntity<TravelerDTO> createTraveler(@Valid @RequestBody TravelerDTO dto){
		
		TravelerDTO createdTraveler = travelerService.createTraveler(dto);
		
		return new ResponseEntity<TravelerDTO>(createdTraveler, HttpStatus.OK);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TravelerDTO> findById(@PathVariable("id") Long id){
		
		Optional<Traveler> traveler = travelerService.findById(id);
		
		TravelerDTO dto = TravelerDTO.builder().name(traveler.get().getName()).build();
		
		if(traveler.isPresent()) {
			return 	new ResponseEntity<TravelerDTO>(dto, HttpStatus.OK);
		}
			return  new ResponseEntity<TravelerDTO>(dto, HttpStatus.OK);
		
	}
	
	
//	 @ExceptionHandler({TravelerException.class})
//	    public ResponseEntity<Object> handleTravelerException(TravelerException ex) {
//
//	        final String message = ex.getMessage();
//	        final String exceptionMessage = ExceptionUtils.getRootCauseMessage(ex);
//
//	        final List<TravelerExceptionHandler.Erro> errors = Arrays.asList(new TravelerExceptionHandler.Erro(message, exceptionMessage));
//
//	        return ResponseEntity.badRequest().body(errors);
//	    }
	
	

}
