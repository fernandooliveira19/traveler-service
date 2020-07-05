package com.fernando.oliveira.traveler.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelerDTO {
	
	private Long id;
	
	private String name;
	
	private String email;
	
	private String document;
	
	private PhoneDTO phone;
	

	public TravelerDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

}