package com.safara.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safara.entities.FerryTypeVehicule;
import com.safara.entities.PassagerInformation;
import com.safara.entities.ReservationFerry;
import com.safara.entities.dto.PassagerinfoDTO;
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
	
	
	@GetMapping("/liste/passagers")	
	public ResponseEntity<List<PassagerinfoDTO>> listPassagerReservation(@RequestParam("idcrypt") String idcrypt)
	{
		try {
			ReservationFerry  ferry = reservationFerryRepository.findByIdcrypt(idcrypt);
			
			List<PassagerinfoDTO> dtos = new ArrayList<PassagerinfoDTO>();
			
			for(PassagerInformation dto : passagerInformationRepository.findByReservation(ferry))
			{
				PassagerinfoDTO p = new PassagerinfoDTO(); 
				
				p.setId(dto.getId());
				p.setTypepersonne(dto.getTypepersonne());
				p.setNom(dto.getNom());
				p.setTel(Integer.parseInt(dto.getTel()));
				p.setAddresse(dto.getAddresse());
				
				dtos.add(p);
			}
			
			return new ResponseEntity<List<PassagerinfoDTO>>(dtos,  HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<List<PassagerinfoDTO>>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	@PostMapping("/passager/information/save")
	public  ResponseEntity<PassagerInformation> savePassager(@RequestParam("idcrypt") String idcrypt, @RequestBody PassagerInformation passager)
	{
		try {
			
			passagerInformationRepository.save(passager);			
			passager.setIdcrypt(encoder.encode(passager.getId()+""));
			
			
			ReservationFerry reservation = reservationFerryRepository.findByIdcrypt(idcrypt);
			passager.setReservation(reservation);
			passagerInformationRepository.save(passager);
			
			Set<PassagerInformation> passagers = new HashSet<PassagerInformation>();
			if(reservation.getPassagers().size() >0 )
			{
				passagers = reservation.getPassagers();
			}
			
			passagers.add(passager);			
			reservation.setPassagers(passagers);
			passager.setDatedepart(reservation.getDatedepart());
		    passager.setReference(reservation.getRefrence());
		    
			reservationFerryRepository.save(reservation);
			
			return new ResponseEntity<PassagerInformation> (passager, HttpStatus.OK);
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return new ResponseEntity<PassagerInformation> (HttpStatus.FORBIDDEN);
		}
	}
	
	@PostMapping("/passager/information/edit")
	public  ResponseEntity<PassagerInformation> editPassager(@RequestBody PassagerInformation passager)
	{
		try {
			PassagerInformation  p = passagerInformationRepository.findById(passager.getId());
			p.setTypepersonne(passager.getTypepersonne());
			p.setNom(passager.getNom());
			
			p.setTel(passager.getTel());
			p.setAddresse(passager.getAddresse());
			
			passagerInformationRepository.save(p);				
			return new ResponseEntity<PassagerInformation> (p, HttpStatus.OK);	
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return new ResponseEntity<PassagerInformation> (HttpStatus.FORBIDDEN);
		}
	}


}
