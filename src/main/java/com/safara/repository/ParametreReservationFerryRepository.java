package com.safara.repository;

import java.util.List;

import com.safara.entities.FerryDestination;
import org.springframework.data.jpa.repository.JpaRepository;

import com.safara.entities.ParametreReservationFerry;

import javax.print.attribute.standard.Destination;

public interface ParametreReservationFerryRepository extends JpaRepository<ParametreReservationFerry, Integer>{
	
	/**
	 * 
	 * @param jour
	 * @return
	 */
	ParametreReservationFerry findByJour(String jour);

	/**
	 *
	 * @param jour
	 * @param destination
	 * @return
	 */
	ParametreReservationFerry  findByJourAndDestination(String jour, FerryDestination destination);

	/**
	 * 
	 */
	List<ParametreReservationFerry> findAll();

}
