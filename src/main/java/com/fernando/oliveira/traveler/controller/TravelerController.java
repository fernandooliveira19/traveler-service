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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/travelers", produces = "application/json")
public class TravelerController {

	@Autowired
	private TravelerService travelerService;

	@ApiOperation(value = "Realiza cadastro de viajante")
	@ApiResponses(value = { 
			@ApiResponse(code = 201, message = "Viajante cadastrado com sucesso"),
			@ApiResponse(code = 400, message = "Dados de cadastro inválidos"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde") })
	@PostMapping
	public ResponseEntity<TravelerDTO> createTraveler(@RequestBody @Valid TravelerDTO travelerDTO) {

		Traveler travelerToSave = travelerDTO.convertToTraveler();

		Traveler createdTraveler = travelerService.save(travelerToSave);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdTraveler.convertToDTO());

	}

	@ApiOperation(value = "Realiza busca de viajante pelo identificador")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Viajante retornado com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde") })
	@GetMapping("/{id}")
	public ResponseEntity<TravelerDTO> findById(@PathVariable("id") Long id) {

		Traveler traveler = travelerService.findById(id);

		return ResponseEntity.status(HttpStatus.OK).body(traveler.convertToDTO());

	}

	@ApiOperation(value = "Realiza busca de todos viajantes cadastrados")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Pesquisa retornou dados com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 404, message = "Pesquisa não retornou resultados"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde") })
	@GetMapping
	public ResponseEntity<PageModel<TravelerDTO>> findAll(@RequestParam Map<String, String> params) {

		PageRequestModel pageRequestModel = new PageRequestModel(params);

		PageModel<TravelerDTO> pageModel = travelerService.findAll(pageRequestModel);

		return ResponseEntity.status(HttpStatus.OK).body(pageModel);

	}

	@ApiOperation(value = "Realiza pesquisa de viajantes por nome")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Pesquisa retornou dados com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 404, message = "Pesquisa não retornou resultados"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde") })
	@GetMapping("/search")
	public ResponseEntity<PageModel<TravelerDTO>> search(
					@RequestParam Map<String, String> params) {

		String name = params.get("name") != null ? params.get("name") : "";
		
		PageRequestModel pageRequestModel = new PageRequestModel(params);

		PageModel<TravelerDTO> result = travelerService.findByNameContainingOrderByNameAsc(name, pageRequestModel);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	
	@ApiOperation(value = "Realiza atualização de dados do viajante")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Atualização realizada com sucesso"),
			@ApiResponse(code = 403, message = "Você não possui permissão para acessar esse recurso"),
			@ApiResponse(code = 404, message = "Pesquisa não retornou resultados"),
			@ApiResponse(code = 500, message = "Ocorreu algum erro inesperado. Tente novamente mais tarde") })

	@PutMapping("/{id}")
	public ResponseEntity<TravelerDTO> update(@PathVariable("id") Long id, @RequestBody TravelerDTO travelerDTO) {

		Traveler travelerToUpdate = travelerDTO.convertToTraveler();
		travelerToUpdate.setId(id);

		Traveler updatedTraveler = travelerService.update(travelerToUpdate);

		return ResponseEntity.status(HttpStatus.OK).body(updatedTraveler.convertToDTO());
	}
}
