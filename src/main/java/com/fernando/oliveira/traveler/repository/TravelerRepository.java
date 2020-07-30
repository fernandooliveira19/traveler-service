package com.fernando.oliveira.traveler.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fernando.oliveira.traveler.domain.Traveler;

@Repository
public interface TravelerRepository extends JpaRepository<Traveler, Long>{

	public Optional<Traveler> findByName(String name);

	public Page<Traveler> findByNameContainingOrderByNameAsc(String name, Pageable pageable);

	public List<Traveler> findAllByOrderByName();

}
