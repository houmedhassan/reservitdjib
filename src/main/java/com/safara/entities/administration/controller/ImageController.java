package com.safara.entities.administration.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.safara.entities.administration.EntiteRepository;
import com.safara.entities.administration.Entites;
import com.safara.entities.administration.Imageentites;
import com.safara.entities.administration.ImageentitesRepository;
import com.safara.entities.administration.dto.Entitiesdto;
import com.safara.entities.administration.dto.Photosdto;
import com.safara.security.entities.User;
import com.safara.security.repository.UserRepository;
import com.safara.service.StorageService;


@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping(value="administration/api")
public class ImageController {
	
	
	@Autowired
	private ImageentitesRepository imageentitesRepository;
	
	@Autowired
	private EntiteRepository entiteRepository;
	
	@Autowired
	private UserRepository UserRepository;
	
	@Autowired
	StorageService storageService;
	
	@GetMapping("/liste/image/by/entite")
	public ResponseEntity<List<Photosdto>> findByEntites(@RequestParam("idcrypt") String idcrypt)
	{
		try {
			Entites entity = entiteRepository.findByIdcrypt(idcrypt);
			
			Entitiesdto dto = new Entitiesdto();
			
			dto.setSfidkey(entity.getIdcrypt());
			dto.setCategorie(entity.getCategorie());
			dto.setPrix(entity.getPrix());
			dto.setNomproprietaire(entity.getNomproprietaire());
			dto.setNom(entity.getNom());
			dto.setRegion(entity.getRegion().getSfidkey());
			
			List<Photosdto> photos = new ArrayList<Photosdto>();
			for(Imageentites image : imageentitesRepository.findByEntiteOrderByPosition(entity))
			{
				Photosdto p = new Photosdto();
				p.setSfidkey(image.getSfidkey());
				p.setAlt(image.getAlt());
				p.setEntity(dto);
				p.setImage(image.getLocation());
				p.setPosition(image.getPosition());
			
				photos.add(p);
			}
			
			return new ResponseEntity<List<Photosdto>>(photos, HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<List<Photosdto>>(HttpStatus.FORBIDDEN);
		}
	}
	

	@CrossOrigin(origins = "*", allowedHeaders = "*")    
	@PostMapping("/liste/image/by/entite/image/save")
    public ResponseEntity<String> uploadFile(@RequestParam("nom") String nom,  @RequestBody MultipartFile file) {
		try {
				storageService.store(file, nom);
				return ResponseEntity.status(HttpStatus.OK).body(file.getOriginalFilename());
		}catch(Exception ex)
		{
		       return ResponseEntity.status(HttpStatus.FORBIDDEN).body(" "+ex.getMessage());
		}
    }

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/liste/image/by/entite/save")
	public ResponseEntity<List<Photosdto>> saveImage(@RequestBody Photosdto photo , Principal principal)
	{
		try {
			
			Entites entity = entiteRepository.findByIdcrypt(photo.getEntity().getSfidkey());
			
			Entitiesdto dto = new Entitiesdto();
			
			dto.setSfidkey(entity.getIdcrypt());
			dto.setCategorie(entity.getCategorie());
			dto.setPrix(entity.getPrix());
			dto.setNomproprietaire(entity.getNomproprietaire());
			dto.setNom(entity.getNom());
			dto.setRegion(entity.getRegion().getSfidkey());
			

			Optional<User> user = UserRepository.findByUsername(principal.getName());
			
			Imageentites imageentity = imageentitesRepository.findBySfidkey(photo.getSfidkey());
			
			if(imageentity == null)
			{
				imageentity = new Imageentites();

				imageentity.setCreated(user.get());
				imageentity.setCreatedat(LocalDateTime.now());
			}else {
				imageentity.setUpdated(user.get());
				imageentity.setUpdatedat(LocalDateTime.now());
			}
			//System.out.println(photo.getFile()+" ******* ");
			//storageService.store(photo.getFile(), entity.getNom());
				

	        Resource file = storageService.loadFile(entity.getNom()+"_"+photo.getImage());
	        System.out.println(file.getURL().toString()+" ******* ");
			imageentity.setLocation(entity.getNom()+"_"+photo.getImage());
			imageentity.setPosition(photo.getPosition());
			imageentity.setAlt(photo.getAlt());
			imageentity.setEntite(entity);
				
			imageentitesRepository.save(imageentity);
			
			List<Photosdto> photos = new ArrayList<Photosdto>();
			
			int i=0;
			for(Imageentites image : imageentitesRepository.findByEntiteOrderByPosition(entity))
			{
				
				if(imageentity.getPosition() == image.getPosition())
				{
					i=1;
				}
				
				if(image != imageentity)
				{
					image.setPosition(image.getPosition()+i);
				}
				imageentitesRepository.save(image);
				
				Photosdto p = new Photosdto();
				p.setSfidkey(image.getSfidkey());
				p.setAlt(image.getAlt());
				p.setEntity(dto);
				p.setImage(image.getLocation());
				p.setPosition(image.getPosition());
			
				photos.add(p);
			}
			
			
			return new ResponseEntity<List<Photosdto>>(photos, HttpStatus.OK);
		}catch(Exception ex) {
			
			ex.printStackTrace();
			return new ResponseEntity<List<Photosdto>>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	@GetMapping("/one/image/by/entite/delete")
	public ResponseEntity<List<Photosdto>> findOne(@RequestParam("idimage") int idimage)
	{
		try {
			
			Imageentites img = imageentitesRepository.findBySfidkey(idimage);
			
			imageentitesRepository.delete(img);
			
			Entites entity = img.getEntite();
			
			Entitiesdto dto = new Entitiesdto();
			
			dto.setSfidkey(entity.getIdcrypt());
			dto.setCategorie(entity.getCategorie());
			dto.setPrix(entity.getPrix());
			dto.setNomproprietaire(entity.getNomproprietaire());
			dto.setNom(entity.getNom());
			dto.setRegion(entity.getRegion().getSfidkey());
			
			List<Photosdto> photos = new ArrayList<Photosdto>();
			for(Imageentites image : imageentitesRepository.findByEntiteOrderByPosition(entity))
			{
				Photosdto p = new Photosdto();
				p.setSfidkey(image.getSfidkey());
				p.setAlt(image.getAlt());
				p.setEntity(dto);
				p.setImage(image.getLocation());
				p.setPosition(image.getPosition());
			
				photos.add(p);
			}
			
			return new ResponseEntity<List<Photosdto>>(photos, HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<List<Photosdto>>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	@GetMapping(value = "/entites/image")
    public ResponseEntity<List<InputStreamResource>> getImage(@RequestParam("idcrypt") String idcrypt) throws IOException {
		
		try {
	    	List<InputStreamResource> inputs = new ArrayList<InputStreamResource>();
	
			Entites entity = entiteRepository.findByIdcrypt(idcrypt);
			for(Imageentites image : imageentitesRepository.findByEntiteOrderByPosition(entity))
			{
	        Resource file = storageService.loadFile(image.getLocation());
	              System.out.println(file.getFilename());
	        inputs.add(new InputStreamResource(file.getInputStream()));
			}
	        /*return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(inputs);*/
			
			return new ResponseEntity<List<InputStreamResource>>(inputs, HttpStatus.OK);
		}catch(Exception ex)
		{ //365488
			return new ResponseEntity<List<InputStreamResource>>(HttpStatus.FORBIDDEN);
		}
        
    }
	
	
}
