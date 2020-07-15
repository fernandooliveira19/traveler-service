package com.fernando.oliveira.traveler.dto;

import com.fernando.oliveira.traveler.domain.Phone;
import com.fernando.oliveira.traveler.domain.Traveler;

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
	
	private Integer prefixPhone;
	
	private String numberPhone; 
	

	public Traveler convertToTraveler() {
		
		Traveler traveler = Traveler.builder()
								.name(name)
								.email(email)
								.document(document)
								.phone(buildPhone())
								.build();
		return traveler;
	}
	
	private Phone buildPhone() {
		Phone phone = Phone.builder()
						.prefix(prefixPhone)
						.number(numberPhone)
						.build();
		return phone;
	}

}