package com.safara.entities.administration;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntiteRepository extends JpaRepository<Entites, Integer> {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Entites findBySfidkey(int id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Entites findByIdcrypt(String id);
	
	/**
	 * 
	 * @param categorie
	 * @return
	 */
	public List<Entites> findByCategorie(String categorie);

}
