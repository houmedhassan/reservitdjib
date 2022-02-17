package com.safara.entities.administration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageentitesRepository extends JpaRepository<Imageentites, Integer> {
	
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public List<Imageentites> findByEntiteOrderByPosition(Entites entity);
	
	
	/**
	 *  @return All
	 */
	public List<Imageentites> findAll();
	
	/**
	 * 
	 * @param idkey
	 * @return
	 */
	public Imageentites findBySfidkey(int idkey);

	
	
}
