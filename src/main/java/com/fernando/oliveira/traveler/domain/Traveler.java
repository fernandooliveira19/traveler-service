package com.fernando.oliveira.traveler.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fernando.oliveira.traveler.dto.TravelerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TRAVELER" )
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Traveler implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="EMAIL", nullable=false)
	private String email;
	
	@Column(name="DOCUMENT")
	private String document;
	
	@OneToOne(mappedBy="traveler", cascade=CascadeType.ALL)
	private Phone phone;
	
	
	public TravelerDTO convertToDTO() {
		TravelerDTO travelerDTO = TravelerDTO.builder()
									.id(id)					
									.name(name)
									.email(email)
									.document(document)
									.prefixPhone(phone.getPrefix())
									.numberPhone(phone.getNumber())
									.build();
		return travelerDTO;
	}
	

}
