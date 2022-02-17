package com.safara.entities.administration;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer>{
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Region findBySfidkey(int id);

}
