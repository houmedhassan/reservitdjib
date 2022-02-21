package com.safara.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safara.entities.FerryTypeVehicule;
import com.safara.entities.PassagerInformation;
import com.safara.entities.ReservationFerry;
import com.safara.repository.PassagerInformationRepository;
import com.safara.repository.ReservationFerryRepository;

@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping(value="external/api")
public class PassagerInformationController {
	
	
	@Autowired
	private PassagerInformationRepository passagerInformationRepository;
	
	@Autowired
	private ReservationFerryRepository reservationFerryRepository;
	
	
	
	@Autowired
    PasswordEncoder encoder;

	@GetMapping("/liste/reservations")	
	public ResponseEntity<List<ReservationFerry>> listtypeVehicule()
	{
		try {
			return new ResponseEntity<List<ReservationFerry>>(reservationFerryRepository.findAll(), HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<List<ReservationFerry>>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	public  ResponseEntity<PassagerInformation> savePassager(@RequestParam("idcrypt") String idcrypt)
	{
		try {
			
			
			
			return new ResponseEntity<PassagerInformation> ( HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<PassagerInformation> (HttpStatus.FORBIDDEN);
		}
	}
	 

}
