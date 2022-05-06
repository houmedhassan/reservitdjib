package com.safara.web;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ch.qos.logback.core.net.SyslogOutputStream;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safara.entities.FerryDestination;
import com.safara.entities.FerryTypeVehicule;
import com.safara.entities.ParametreReservationFerry;
import com.safara.entities.ReservationFerry;
import com.safara.entities.administration.FerryTarif;
import com.safara.entities.administration.RegionRepository;
import com.safara.entities.dto.ReservationFerryDTO;
import com.safara.entities.dto.ReservationFerryMobileDTO;
import com.safara.entities.dto.ReservationFerryResultDTO;
import com.safara.repository.FerryDestinationRepository;
import com.safara.repository.FerryTarifRepository;
import com.safara.repository.FerryTypeCarsRepository;
import com.safara.repository.ParametreReservationFerryRepository;
import com.safara.repository.PassagerInformationRepository;
import com.safara.repository.ReservationFerryRepository;

@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping(value="external/api")
public class ReservationFerryController {
	
	
	
	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private ParametreReservationFerryRepository parametreReservationFerryRepository;
	
	
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

	
	
	public ResponseEntity<ParametreReservationFerry> findJourDestination(@RequestParam("date") LocalDate date)
	{
		try {
			
			String jour = date.getDayOfWeek().name();
			
			return new ResponseEntity<ParametreReservationFerry>(HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<ParametreReservationFerry>(HttpStatus.FORBIDDEN);
		}
	}
	
	

	@GetMapping("/liste/Parametre/destination")
	public ResponseEntity<List<ParametreReservationFerry>> findJourDestinations()
	{
		try {
			return new ResponseEntity<List<ParametreReservationFerry>>(parametreReservationFerryRepository.findAll(), HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<List<ParametreReservationFerry>>(HttpStatus.FORBIDDEN);
		}
	} 
	
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/liste/destinations")
	public ResponseEntity<List<FerryDestination>> destinations()
	{
		try {
						
			return new ResponseEntity<List<FerryDestination>>(ferryDestinationRepository.findAll(), HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<List<FerryDestination>>(HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/liste/vehicules")
	public ResponseEntity<List<FerryTypeVehicule>> listtypeVehicule()
	{
		try {
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
	
	/**
	 * 
	 * @param reservation
	 * @return
	 */
	@PostMapping("/reservation/save")
	public  ResponseEntity<ReservationFerryResultDTO> saveReservation(@RequestBody ReservationFerryResultDTO  reservation)
	{
		try {
			
				ReservationFerry ferry = new ReservationFerry();
								
				ferry.setDestination(reservation.getDestination());				
				
			
				ferry.setTypevoyagename(reservation.getTypevoyagename());
				ferry.setDatedepart(reservation.getDatedepart());
				ferry.setDateretour(reservation.getDatedepart());
				
				ferry.setNbadult(reservation.getNbadult());
				ferry.setNbchild(reservation.getNbchild());
				ferry.setStatus("EN COURS DE VALIDATON");
	
				
				
				reservationFerryRepository.save(ferry);
			//}

				ferry.setIdcrypt(encoder.encode(ferry.getSafkey()+""));
				
				reservationFerryRepository.save(ferry);
				
			return new ResponseEntity<ReservationFerryResultDTO>(HttpStatus.OK);
		}catch(Exception ex)
		{
			return new  ResponseEntity<ReservationFerryResultDTO>(HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 
	 * 
	 * @param ferry
	 * @return
	 */
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
				res.setTotal(total*2);
				//resultat.add(res);
				
				
				
		
			}
			
			ReservationFerry reservation  = new ReservationFerry();
			reservation.setDestination(res.getDestination());
			reservation.setTypevoyage(ferry.getTypebooking());
			reservation.setDatedepart(res.getDatedepart());
			reservation.setDateretour(LocalDate.parse(ferry.getDateretour()));
			reservation.setNbadult(res.getNbadult());
			reservation.setNbchild(res.getNbchild());
			reservation.setTypevoyagename(res.getTypevoyagename());
			reservation.setEtape("INITIAL");
			
			reservation.setMontant(res.getTotal());
			reservationFerryRepository.save(reservation);
			
			reservation.setIdcrypt(encoder.encode(reservation.getSafkey()+""));			
			reservationFerryRepository.save(reservation);
			res.setIdcrypt(reservation.getIdcrypt());
			
			
			return new ResponseEntity<ReservationFerryResultDTO>(res, HttpStatus.OK);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return new ResponseEntity<ReservationFerryResultDTO>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	/**
	 * 
	 * @param idcrypt
	 * @return Object of Reservation find by idcrypt - Using by Booking Details API,
	 */
	@GetMapping("/find/reservation/ferry/one")
	public ResponseEntity<ReservationFerry> findReservation(@RequestParam("idcrypt") String idcrypt)
	{
		try {
			
			ReservationFerry ferry = reservationFerryRepository.findByIdcrypt(idcrypt);
			
			return new ResponseEntity<ReservationFerry>(ferry, HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<ReservationFerry>(HttpStatus.FORBIDDEN);
		}
	}
	
	/**
	 * 
	 * @param nom
	 * @param tel
	 * @param id
	 * @return
	 */
	@PostMapping("/find/reservation/ferry/save")
	public ResponseEntity<ReservationFerry> editReservation(@RequestParam("nom") String nom, @RequestParam("tel") String tel, @RequestBody String  id)
	{
		try {
			
			ReservationFerry ferry = reservationFerryRepository.findByIdcrypt(id);
			ferry.setOwner(nom);
			ferry.setOwnertel(tel);
			ferry.setRefrence(100000+ferry.getSafkey()+""+ferry.getDestination().substring(0, 3));
			reservationFerryRepository.save(ferry);
			
			return new ResponseEntity<ReservationFerry>(ferry, HttpStatus.OK);
		}catch(Exception ex)
		{
	
			return new ResponseEntity<ReservationFerry>(HttpStatus.FORBIDDEN);
		}
		
	}

	/**
	 *
	 * @param mobile
	 * @return  method to save Reservation of the mobile APPS
	 */
	@PostMapping("/find/save/reservation")
	public ResponseEntity<ReservationFerryResultDTO> saveMobileReservation(@RequestBody  ReservationFerryMobileDTO mobile){
		
		try {
			System.out.println(mobile.getNbetudiant()+" je suis ici ");
			System.out.println(mobile.toString());
			
			double somme=0;
			
			List<FerryTarif> tarrifs =ferryTarifRepository.findAll();			
			
			for(FerryTarif tarif : tarrifs)
			{
				if(mobile.getNbadulte() > 0 && tarif.getSfidkey() == 1)
				{  somme += tarif.getMontant() * mobile.getNbadulte();}

				if(mobile.getNbetudiant() > 0 && tarif.getSfidkey() == 2)
				{  somme += tarif.getMontant() * mobile.getNbetudiant();}
				
				if(mobile.getNbenfant() > 0 && tarif.getSfidkey() == 3)
				{ somme += tarif.getMontant() * mobile.getNbenfant();}
			}

			ReservationFerryResultDTO res = new ReservationFerryResultDTO();
			ReservationFerry reservation  = new ReservationFerry();
			
			if(mobile.getVehicule()!=null)
			{
				FerryTypeVehicule tarifV = ferryTypeCarsRepository.findBySafid(mobile.getVehicule().getSafid());				
				somme+= tarifV.getPrice();
				reservation.setVehicule(mobile.getVehicule().getSafid());
				reservation.setVehiculetype(mobile.getVehicule().getTypecars());
				res.setVehiculetype(reservation.getVehiculetype());				
			}
			
			reservation.setDestination(mobile.getDestination().getName());
			reservation.setTypevoyage(1);
			reservation.setDatedepart(mobile.getDatedepart());
			reservation.setNbadult(mobile.getNbadulte());
			reservation.setNbchild(mobile.getNbenfant());
			reservation.setNbstudent(mobile.getNbetudiant());
			reservation.setTypevoyagename("ALLER-SIMPLE");
			reservation.setEtape("INITIAL");

			reservation.setMontant(somme);
			reservationFerryRepository.save(reservation);
			
			reservation.setIdcrypt(encoder.encode(reservation.getSafkey()+""));			
			reservationFerryRepository.save(reservation);				
			
			res.setDestination(reservation.getDestination());
			res.setTypevoyagename("ALLER-SIMPLE");
			res.setDatedepart(reservation.getDatedepart());
			res.setNbadult(reservation.getNbadult());
			res.setNbchild(reservation.getNbchild());
			res.setNbstudent((reservation.getNbstudent()));
			res.setVehiculetype(reservation.getVehiculetype());
			res.setSomme(reservation.getMontant());
			res.setTotal(reservation.getMontant());
			res.setIdcrypt(reservation.getIdcrypt());
			
			return new ResponseEntity<ReservationFerryResultDTO>(res, HttpStatus.OK);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return new ResponseEntity<ReservationFerryResultDTO>(HttpStatus.FORBIDDEN);
		}
		
	}

	/**
	 *
	 * @param idcrypt
	 * @return  object of reservation finding by idcrypt - Using by Booking Details API, PASSGER API,
	 */
	@GetMapping("/find/reservation/ferry/mobile/one")
	public ResponseEntity<ReservationFerryResultDTO> findReservationMobile(@RequestParam("idcrypt") String idcrypt)
	{
		try {
			ReservationFerry reservation = reservationFerryRepository.findByIdcrypt(idcrypt);
			

			ReservationFerryResultDTO res = new ReservationFerryResultDTO();
			
			if(reservation.getVehicule()!=0)
			{
				res.setVehiculetype(reservation.getVehiculetype());				
			}
			
			res.setDestination(reservation.getDestination());
			res.setTypevoyagename("ALLER-SIMPLE");
			res.setDatedepart(reservation.getDatedepart());
			res.setNbadult(reservation.getNbadult());
			res.setNbchild(reservation.getNbchild());
			res.setNbstudent(reservation.getNbstudent());
			res.setVehiculetype(reservation.getVehiculetype());
			res.setSomme(reservation.getMontant());
			res.setTotal(reservation.getMontant());
			res.setIdcrypt(reservation.getIdcrypt());
					
			
			return new ResponseEntity<ReservationFerryResultDTO>(res, HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<ReservationFerryResultDTO>(HttpStatus.FORBIDDEN);
		}
	}
		
	
	/**
	 * VERIFIER SI UN BOOKING EST POSSIBLE POUR LA DESTINATION 
	 */
	@PostMapping("/find/reservation/ferry/verification/destination")
	public ResponseEntity<ParametreReservationFerry> verificationDestination(@RequestParam("date") String date, @RequestBody FerryDestination destination)
	{
		try {
			String jour = LocalDate.parse(date).getDayOfWeek().name();
			System.out.println("DATE : "+date+ " JOUR : "+jour);
			
			ParametreReservationFerry ferry = parametreReservationFerryRepository.findByJourAndDestination(jour, destination);

			if(ferry == null)
			{
				FerryDestination destination1 = ferryDestinationRepository.findTop1ByArriveAndName(destination.getName(), destination.getArrive());

				ferry = parametreReservationFerryRepository.findByJourAndDestination(jour, destination1);
			}
			
			return new ResponseEntity<ParametreReservationFerry>(ferry, HttpStatus.OK);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return new ResponseEntity<ParametreReservationFerry>(HttpStatus.FORBIDDEN);
		}

	}


	@GetMapping("reservations/en/cours")
	public ResponseEntity<List<ReservationFerry>> listReservation(Principal principal)
	{
		try{
			List<ReservationFerry> reservations = reservationFerryRepository.findByEtape("INITIAL");
			return new ResponseEntity<List<ReservationFerry>>(reservations, HttpStatus.OK);
		}catch (Exception ex){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
}
