package com.safara.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safara.entities.ParametreReservationFerry;

public interface ParametreReservationFerryRepository extends JpaRepository<ParametreReservationFerry, Integer>{
	
	/**
	 * 
	 * @param jour
	 * @return
	 */
	ParametreReservationFerry findByJour(String jour);
	
	/**
	 * 
	 */
	List<ParametreReservationFerry> findAll();

}
