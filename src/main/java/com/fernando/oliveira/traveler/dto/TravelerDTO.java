package com.fernando.oliveira.traveler.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
	
	@NotBlank(message="Nome é obrigatório")
	private String name;
	
	@Email(message="Email inválido")
	private String email;
	
	private String document;
	
	@NotBlank(message="DDD é obrigatório")
	private Integer prefixPhone;
	
	@NotBlank(message="Telefone é obrigatório")
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