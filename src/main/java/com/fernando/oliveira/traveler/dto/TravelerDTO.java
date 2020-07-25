package com.fernando.oliveira.traveler.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fernando.oliveira.traveler.domain.Phone;
import com.fernando.oliveira.traveler.domain.Traveler;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="Viajante")
public class TravelerDTO {
	
	@ApiModelProperty(hidden=true)
	private Long id;
	
	@ApiModelProperty(value= "Nome do viajante")
	@NotBlank(message="Nome é obrigatório")
	@NotNull(message="Nome é obrigatório")
	private String name;
	
	@ApiModelProperty(value= "Email do viajante")
	@Email(message="Email inválido")
	@NotBlank(message="Email é obrigatório")
	private String email;
	
	@ApiModelProperty(value= "Documento do viajante")
	private String document;
	
	@ApiModelProperty(value= "DDD do telefone do viajante")
	@NotBlank(message="DDD é obrigatório")
	@NotNull(message="DDD é obrigatório")
	@Size(min=2, max=2, message="DDD deve ter 2 dígitos")
	private Integer prefixPhone;
	
	@ApiModelProperty(value= "Numero do telefone do viajante")
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