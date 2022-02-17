package com.safara.entities.administration.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

import com.safara.entities.administration.EntiteRepository;
import com.safara.entities.administration.Entites;
import com.safara.entities.administration.Region;
import com.safara.entities.administration.RegionRepository;
import com.safara.entities.administration.dto.Entitiesdto;
import com.safara.security.entities.RoleName;
import com.safara.security.entities.RoleUser;
import com.safara.security.entities.User;
import com.safara.security.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping(value="administration/api")
public class EntitesController {
	
	
	@Autowired
	private RegionRepository regionRepository;
	
	@Autowired
	private UserRepository UserRepository;
	
	@Autowired
	private EntiteRepository entiteRepository;
	
	  @Autowired
	  PasswordEncoder encoder;
	
	/**
	 * list of region
	 * @return
	 */
	@GetMapping("/liste/region")
	public ResponseEntity<List<Region>> listtypeVehicule()
	{
		try {
			return new ResponseEntity<List<Region>>(regionRepository.findAll(), HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<List<Region>>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	@GetMapping("/liste/campement/touristique")
	public ResponseEntity<List<Entites>> campementstouristique()
	{
		try {
			return new ResponseEntity<List<Entites>>(entiteRepository.findByCategorie("CAMPEMENT TOURISTIQUE"), HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<List<Entites>>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	@GetMapping("/liste/campement/touristique/edit")
	public ResponseEntity<Entitiesdto> editcampementtouristique(@RequestParam("idcrypt") String idcrypt)
	{
		try {
			
			Entites en =  entiteRepository.findByIdcrypt(idcrypt);
			
			Entitiesdto dto = new Entitiesdto();
			dto.setSfidkey(en.getIdcrypt());
			dto.setCategorie(en.getCategorie());
			dto.setPrix(en.getPrix());
			dto.setNomproprietaire(en.getNomproprietaire());
			dto.setNom(en.getNom());
			dto.setRegion(en.getRegion().getSfidkey());
			
			return new ResponseEntity<Entitiesdto>(dto, HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<Entitiesdto>(HttpStatus.FORBIDDEN);
		}
	} 
	
	
	@PostMapping("/entities/save")
	public ResponseEntity<Entitiesdto>entitiesSave(@RequestBody Entitiesdto entity, Principal principal)
	{
		try {
			
			Optional<User> user = UserRepository.findByUsername(principal.getName());
			
			boolean admin =false;
			for(RoleUser role : user.get().getRoles())
			{
				if(role.getName().equals(RoleName.ROLE_ADMIN))
				{
					admin =true;
				}
			}
			
			if(admin)
			{			
				Entites en =  entiteRepository.findByIdcrypt(entity.getSfidkey());				
				if(en == null) { 
					en = new Entites();
					en.setCreated(user.get());
					en.setUpdated(user.get());
					en.setUpdatedat(LocalDateTime.now());
					en.setCreatedat(LocalDateTime.now());
				}else {
					en.setUpdated(user.get());
					en.setUpdatedat(LocalDateTime.now());
				}
				en.setCategorie(entity.getCategorie());
				en.setPrix(entity.getPrix());
				Region region = regionRepository.findBySfidkey(entity.getRegion());
				en.setRegion(region);
				en.setNomproprietaire(entity.getNomproprietaire());
				en.setNom(entity.getNom());
				
				entiteRepository.save(en);
				encoder = new BCryptPasswordEncoder(12);
				en.setIdcrypt(encoder.encode(en.getSfidkey()+""));

				entiteRepository.save(en);
				entity.setSfidkey(en.getIdcrypt());
				
			}
			
			return new ResponseEntity<Entitiesdto>(entity, HttpStatus.OK);
		}catch(Exception ex)
		{
			return new ResponseEntity<Entitiesdto>(HttpStatus.FORBIDDEN);
		}
	}
	
		
	
	
	
}
