package com.fernando.oliveira.traveler.controller;

import java.util.List;
import java.util.stream.Collectors;

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
	public ResponseEntity<TravelerDTO> createTraveler(@RequestBody @Valid TravelerDTO dto) {

		Traveler travelerToSave = dto.convertToTraveler();

		Traveler createdTraveler = travelerService.save(travelerToSave);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdTraveler.convertToDTO());

	}

	@GetMapping("/{id}")
	public ResponseEntity<TravelerDTO> findById(@PathVariable("id") Long id) {

		Traveler traveler = travelerService.findById(id);

		return ResponseEntity.status(HttpStatus.OK).body(traveler.convertToDTO());

	}

	@GetMapping
	public ResponseEntity<List<TravelerDTO>> findAll() {

		List<Traveler> result = travelerService.findAll();

		List<TravelerDTO> resultDTO = convertTravelersToListDTO(result);

		return ResponseEntity.status(HttpStatus.OK).body(resultDTO);

	}

	@GetMapping("/search/{name}")
	public ResponseEntity<List<TravelerDTO>> findTravelersByName(@PathVariable("name") String name) {

		List<Traveler> result = travelerService.findByNameContainingOrderByNameAsc(name);

		List<TravelerDTO> resultDTO = convertTravelersToListDTO(result);

		return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
	}

	private List<TravelerDTO> convertTravelersToListDTO(List<Traveler> result) {
		List<TravelerDTO> resultDTO = result.stream().map(e -> e.convertToDTO()).sorted().collect(Collectors.toList());
		return resultDTO;
	}

}
