package com.safara.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safara.entities.administration.FerryTarif;

public interface FerryTarifRepository extends JpaRepository<FerryTarif, Integer>{
	
	/**
	 * 
	 * @return
	 */
	public FerryTarif findByType(String type);
	
	
	/**
	 * 
	 */
	public List<FerryTarif> findAll();

}
