package com.safara.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safara.entities.FerryTypeVehicule;

public interface FerryTypeCarsRepository extends JpaRepository<FerryTypeVehicule, Integer>{

	/**
	 * 
	 * @return
	 */
	public FerryTypeVehicule  findBySafid(int id);
	
	/**
	 * @return all list 
	 */
	public List<FerryTypeVehicule> findAll();
	
	
}
