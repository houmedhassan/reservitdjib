package com.safara.entities.administration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safara.entities.PassagerInformation;
import com.safara.entities.ReservationFerry;
import com.safara.entities.administration.RegionRepository;
import com.safara.repository.FerryDestinationRepository;
import com.safara.repository.FerryTarifRepository;
import com.safara.repository.FerryTypeCarsRepository;
import com.safara.repository.PassagerInformationRepository;
import com.safara.repository.ReservationFerryRepository;

@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping(value="administration/api")
public class FerryAdministration {
	
	
	@Autowired
	private RegionRepository regionRepository;
	
	
	@Autowired
	private ReservationFerryRepository reservationFerryRepository;
	
	@Autowired
	private FerryTypeCarsRepository ferryTypeCarsRepository;
	
	@Autowired
	private FerryTarifRepository ferryTarifRepository;
	
	@Autowired
	private FerryDestinationRepository ferryDestinationRepository;	
	
	@Autowired
	private PassagerInformationRepository passagerInformationRepository;
	
	@Autowired
    PasswordEncoder encoder;
	
	@GetMapping("/find/admireservation/ferry")
	public ResponseEntity<List<ReservationFerry>> findAllReservation()
	{
		try {
			return new ResponseEntity<List<ReservationFerry>>(reservationFerryRepository.findAll(), HttpStatus.OK);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return new ResponseEntity<List<ReservationFerry>>(HttpStatus.FORBIDDEN);
		}
	}
	
	

	
	@GetMapping("/find/reservation/passagers")
	public ResponseEntity<List<PassagerInformation>> passagers()
	{
		try {
			return new ResponseEntity<List<PassagerInformation>>(passagerInformationRepository.findAll(), HttpStatus.OK);
		}catch(Exception ex)
		{
	
			return new ResponseEntity<List<PassagerInformation>>(HttpStatus.FORBIDDEN);
		}
		
	} 
	
	
	
	
	

}
