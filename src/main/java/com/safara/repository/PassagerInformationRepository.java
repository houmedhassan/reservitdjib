package com.safara.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safara.entities.PassagerInformation;
import com.safara.entities.ReservationFerry;

public interface PassagerInformationRepository extends JpaRepository<PassagerInformation, Integer>{
	
	
	/**
	 * 
	 */
	public List<PassagerInformation> findAll();

	
	/**
	 * 
	 * @param reservation
	 * @return
	 */
	public List<PassagerInformation> findByReservation(ReservationFerry reservation);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public PassagerInformation findById(int id);
}
