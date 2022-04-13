package com.safara.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.safara.entities.ReservationFerry;

public interface ReservationFerryRepository extends JpaRepository<ReservationFerry, Integer>{
	
	/**
	 * 
	 * @return
	 */
	public List<ReservationFerry> findAll();
	
	/**
	 * 
	 * @param datedepart
	 * @return
	 */
	public ReservationFerry findByDatedepart(LocalDate datedepart);

	/**
	 * 
	 * @param idcrypt
	 * @return
	 */
	public ReservationFerry findByIdcrypt(String idcrypt);
}
