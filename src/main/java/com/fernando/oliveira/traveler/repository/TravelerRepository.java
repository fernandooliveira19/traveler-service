package com.fernando.oliveira.traveler.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernando.oliveira.traveler.domain.Traveler;

public interface TravelerRepository extends JpaRepository<Traveler, Long>{

	Optional<Traveler> findByName(String name);

	Optional<List<Traveler>> findByNameContainingOrderByNameAsc(String partOfName);

}
