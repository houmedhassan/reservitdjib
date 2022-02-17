package com.safara.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safara.entities.FerryDestination;

public interface FerryDestinationRepository extends JpaRepository<FerryDestination, Integer> {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public FerryDestination findBySfidkey(int id);

}
