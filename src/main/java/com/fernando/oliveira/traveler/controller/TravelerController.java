package com.fernando.oliveira.traveler.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.oliveira.traveler.domain.Traveler;
import com.fernando.oliveira.traveler.dto.TravelerDTO;
import com.fernando.oliveira.traveler.model.PageModel;
import com.fernando.oliveira.traveler.model.PageRequestModel;
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
	public ResponseEntity<PageModel<TravelerDTO>> findAll(
			@RequestParam Map<String, String> params) {

		PageRequestModel pageRequestModel= new PageRequestModel(params);
		
		PageModel<TravelerDTO> pageModel = travelerService.findAll(pageRequestModel);

		
		return ResponseEntity.status(HttpStatus.OK).body(pageModel);

	}

	@GetMapping("/search/{name}")
	public ResponseEntity<PageModel<TravelerDTO>> findTravelersByName(@PathVariable("name") String name, Map<String, String> params) {

		PageRequestModel pageRequestModel = new PageRequestModel(params);
		
		PageModel<TravelerDTO> result = travelerService.findByNameContainingOrderByNameAsc(name, pageRequestModel);



		return ResponseEntity.status(HttpStatus.OK).body(result);
	}


	@PutMapping("/{id}")
	public ResponseEntity<TravelerDTO> update(@PathVariable("id") Long id, @RequestBody TravelerDTO travelerDTO){
		
		Traveler travelerToUpdate = travelerDTO.convertToTraveler();
		travelerToUpdate.setId(id);
		
		Traveler updatedTraveler = travelerService.update(travelerToUpdate);
		
		return ResponseEntity.status(HttpStatus.OK).body(updatedTraveler.convertToDTO());
	}
}
