package com.fernando.oliveira.traveler.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@NotNull(message="Nome é obrigatório")
	private String name;
	
	@Email(message="Email inválido")
	@NotBlank(message="Email é obrigatório")
	private String email;
	
	private String document;
	
	@NotBlank(message="DDD é obrigatório")
	@NotNull(message="DDD é obrigatório")
	@Size(min=2, max=2, message="DDD deve ter 2 dígitos")
	private Integer prefixPhone;
	
	@NotBlank(message="Telefone é obrigatório")
	@NotNull(message="Telefone é obrigatório")
	@Size(min=8, max=9, message="Telefone inválido")
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