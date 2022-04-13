package com.safara.web;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safara.entities.FerryDestination;
import com.safara.entities.FerryTypeVehicule;
import com.safara.entities.administration.FerryTarif;
import com.safara.repository.FerryDestinationRepository;
import com.safara.repository.FerryTarifRepository;
import com.safara.repository.FerryTypeCarsRepository;
import com.safara.security.repository.RoleRepository;
import com.safara.security.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping(value="external/api/datas")
public class HomeController {
	
	
	@Autowired
	private FerryTypeCarsRepository ferryTypeCarsRepository;
	
	@Autowired
	private FerryTarifRepository ferryTarifRepository;
	
	@Autowired
	private FerryDestinationRepository ferryDestinationRepository;
	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@GetMapping("/liste/tarrif/save")
	public ResponseEntity<List<FerryTarif>> tarif_ferry()
	{
		try {
			
			FerryTarif tarrif = new FerryTarif();
			tarrif.setType("ADULT");
			tarrif.setMontant(700);
			
			 ferryTarifRepository.save(tarrif);
			
			 tarrif = new FerryTarif();
			 tarrif.setType("ETUDIANT");
			 tarrif.setMontant(400);
				
			 ferryTarifRepository.save(tarrif);
			
			return new ResponseEntity<List<FerryTarif>>(ferryTarifRepository.findAll(), HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<List<FerryTarif>>(HttpStatus.FORBIDDEN);
		}
	}
	
	@GetMapping("/liste/vehicule/save")
	public ResponseEntity<List<FerryTypeVehicule>> typecars()
	{
		try {
			
			FerryTypeVehicule tarrif = new FerryTypeVehicule();
			tarrif.setTypecars("Vehicule 4*4");
			tarrif.setPrice(5000);
			tarrif.setCreatedat(LocalDateTime.now());
			ferryTypeCarsRepository.save(tarrif);
			
			tarrif = new FerryTypeVehicule();
			tarrif.setTypecars("Poids Lourd");
			tarrif.setPrice(10000);
			tarrif.setCreatedat(LocalDateTime.now());
			ferryTypeCarsRepository.save(tarrif);
			
			tarrif = new FerryTypeVehicule();
			tarrif.setTypecars("Petite Vehicule");
			tarrif.setPrice(3000);
			tarrif.setCreatedat(LocalDateTime.now());
			ferryTypeCarsRepository.save(tarrif);
			
			tarrif = new FerryTypeVehicule();
			tarrif.setTypecars("Mini-Bus");
			tarrif.setPrice(5000);
			tarrif.setCreatedat(LocalDateTime.now());
			ferryTypeCarsRepository.save(tarrif);	
			
			tarrif = new FerryTypeVehicule();
			tarrif.setTypecars("Bus");
			tarrif.setPrice(10000);
			tarrif.setCreatedat(LocalDateTime.now());
			ferryTypeCarsRepository.save(tarrif);
			
			return new ResponseEntity<List<FerryTypeVehicule>>(ferryTypeCarsRepository.findAll(), HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<List<FerryTypeVehicule>>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/liste/destination/save")
	public ResponseEntity<List<FerryDestination>> destinations()
	{
		try {
		
			
			FerryDestination ferry = new FerryDestination();
			
			ferry.setName("TADJOURAH");
			ferry.setArrive("DJIBOUTI");
			ferry.setCreatedat(LocalDateTime.now());
			ferry.setUpdatedat(LocalDateTime.now());
			
			
			ferryDestinationRepository.save(ferry);
			
			ferry = new FerryDestination();
			
			ferry.setName("OBOCK");
			ferry.setArrive("DJIBOUTI");
			ferry.setCreatedat(LocalDateTime.now());
			ferry.setUpdatedat(LocalDateTime.now());
			
			
			ferryDestinationRepository.save(ferry);
			
			ferry = new FerryDestination();
			
			ferry.setName("DJIBOUTI");
			ferry.setArrive("TADJOURAH");
			ferry.setCreatedat(LocalDateTime.now());
			ferry.setUpdatedat(LocalDateTime.now());
			
			
			ferryDestinationRepository.save(ferry);
			
			ferry = new FerryDestination();
			
			ferry.setName("DJIBOUTI");
			ferry.setArrive("OBOCK");
			ferry.setCreatedat(LocalDateTime.now());
			ferry.setUpdatedat(LocalDateTime.now());
			
			
			ferryDestinationRepository.save(ferry);
						
			return new ResponseEntity<List<FerryDestination>>( ferryDestinationRepository.findAll(), HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<List<FerryDestination>>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	

}
