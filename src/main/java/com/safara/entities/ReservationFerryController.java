package com.safara.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safara.entities.administration.FerryTarif;
import com.safara.entities.administration.RegionRepository;
import com.safara.entities.dto.ReservationFerryDTO;
import com.safara.entities.dto.ReservationFerryResultDTO;
import com.safara.repository.FerryDestinationRepository;
import com.safara.repository.FerryTarifRepository;
import com.safara.repository.FerryTypeCarsRepository;
import com.safara.repository.ReservationFerryRepository;

@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping(value="external/api")
public class ReservationFerryController {
	
	
	
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
    PasswordEncoder encoder;

	@GetMapping("/liste/vehicule")
	public ResponseEntity<List<FerryTypeVehicule>> listtypeVehicule()
	{
		try {
			
			
			return new ResponseEntity<List<FerryTypeVehicule>>(ferryTypeCarsRepository.findAll(), HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<List<FerryTypeVehicule>>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	@GetMapping("/idcrypt")
	public  ResponseEntity<String> idcrypt()
	{
		try {
			
			 encoder = new BCryptPasswordEncoder(12);
			 
			return new  ResponseEntity<String>( encoder.encode(LocalDateTime.now().toString()), HttpStatus.OK);
		}catch(Exception ex)
		{
			return new  ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}
	}
	
	@PostMapping("/reservation/save")
	public  ResponseEntity<ReservationFerryResultDTO> saveReservation(@RequestBody ReservationFerryResultDTO  reservation)
	{
		try {
			/*
			for(ReservationFerryResultDTO res : reservation)
			{*/
				ReservationFerry ferry = new ReservationFerry();
				
				//FerryDestination  region = ferryDestinationRepository.findBySfidkey(reservation.getDestination()); 
				
				ferry.setDestination(reservation.getDestination());
				
				/*if()
				{
					
				}*/
				
				//ferry.setTypevoyage(reservation.getTypebooking());
				ferry.setTypevoyagename(reservation.getTypevoyagename());
				ferry.setDatedepart(reservation.getDatedepart());
				ferry.setDateretour(reservation.getDatedepart());
				
				ferry.setNbadult(reservation.getNbadult());
				ferry.setNbchild(reservation.getNbchild());
				ferry.setStatus("EN COURS DE VALIDATON");
	
				//ferry.setVehicule(reservation.getVehicule());
				
				//ferry.setVehiculetype("");
				
				reservationFerryRepository.save(ferry);
			//}
			
			return new ResponseEntity<ReservationFerryResultDTO>(HttpStatus.OK);
		}catch(Exception ex)
		{
			return new  ResponseEntity<ReservationFerryResultDTO>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	@PostMapping("/find/reservation/ferry")
	public ResponseEntity<ReservationFerryResultDTO> findResultat(@RequestBody ReservationFerryDTO ferry)
	{
		try {
			
			//System.out.println(ferry.getVehicule().getCode());
			ReservationFerryResultDTO res = new ReservationFerryResultDTO();
			
			int somme =0;
			
			int nbadult = ferry.getNbadult();
			
			FerryTarif tarrifadult =ferryTarifRepository.findByType("ADULT");

			somme+= tarrifadult.getMontant()*nbadult;
			FerryTypeVehicule typevehicule = null;
			if(ferry.getVehicule() != 0)
			{
				 typevehicule = ferryTypeCarsRepository.findBySafid(1);
				somme+= typevehicule.getPrice();
			}
			
			if(ferry.getTypebooking() == 1)
			{
				res = new ReservationFerryResultDTO();
			
				FerryDestination destination = ferryDestinationRepository.findBySfidkey(ferry.getDestination());
				res.setDestination(destination.getName());
			    res.setTypevoyagename("ALLER-SIMPLE");
			    res.setDatedepart(LocalDate.parse(ferry.getDatedepart()));
				res.setNbadult(nbadult);
				if(ferry.getNbchild()!= 0)
				{
				  res.setNbchild(ferry.getNbchild());
				}
				if(ferry.getVehicule()!= 0)
				{
					res.setVehiculetype(typevehicule.getTypecars());
				}
				res.setSomme(somme);
				res.setTotal(somme);
				
				//resultat.add(res);
				
			}else {
				double  total = 0;
				res = new ReservationFerryResultDTO();
				FerryDestination destination = ferryDestinationRepository.findBySfidkey(ferry.getDestination());
				res.setDestination(destination.getName());
			    res.setTypevoyagename("ALLER-RETOUR");
			    res.setDatedepart(LocalDate.parse(ferry.getDatedepart()));
				res.setNbadult(nbadult);
				if(ferry.getNbchild()!= 0)
				{
				  res.setNbchild(ferry.getNbchild());
				}
				if(ferry.getVehicule()!= 0)
				{
					res.setVehiculetype(typevehicule.getTypecars());
				}
				res.setSomme(somme);
				total += somme;
				res.setTotal(total);
				//resultat.add(res);
		
			}
			
			
			
			return new ResponseEntity<ReservationFerryResultDTO>(res, HttpStatus.OK);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return new ResponseEntity<ReservationFerryResultDTO>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	
	
	
}
