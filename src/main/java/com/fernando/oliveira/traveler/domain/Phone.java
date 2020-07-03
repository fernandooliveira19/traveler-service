package com.fernando.oliveira.traveler.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="PHONE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phone implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="PREFIX", nullable=false)
	private Integer prefix;
	
	@Column(name="NUMBER", nullable=false)
	private String number;
	
	/**
	 * Relationships
	 */
	@OneToOne
	@JoinColumn(name="TRAVELER_ID", insertable = true, updatable = true)
	@JsonIgnore
	private Traveler traveler;
}
