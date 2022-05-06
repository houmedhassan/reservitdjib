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

	/**
	 *
	 * @param arrive
	 * @return
	 */
	public FerryDestination findByArrive(String arrive);

	/**
	 *
	 * @param arrive
	 * @param name
	 * @return
	 */
	public FerryDestination findTop1ByArriveAndName(String arrive, String name);


	/**
	 *
	 * @param destination
	 * @return
	 */
	public FerryDestination findByName(String destination);

}
