package com.safara.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safara.entities.administration.FerryTarif;

public interface FerryTarifRepository extends JpaRepository<FerryTarif, Integer>{
	
	/**
	 * 
	 * @return
	 */
	public FerryTarif findByType(String type);

}
