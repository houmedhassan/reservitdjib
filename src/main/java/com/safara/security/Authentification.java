package com.safara.security;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import com.safara.security.entities.LoginForm;
import com.safara.security.entities.RoleName;
import com.safara.security.entities.RoleUser;
import com.safara.security.entities.User;
import com.safara.security.jwt.JwtProvider;
import com.safara.security.jwt.JwtResponse;
import com.safara.security.repository.RoleRepository;
import com.safara.security.repository.UserHistoryRepository;
import com.safara.security.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 360)
@RestController
@RequestMapping(value="authentification/api")
public class Authentification {

	  @Autowired
	  AuthenticationManager authenticationManager;
	 
	  @Autowired
	  UserRepository userRepository;
	 
	  @Autowired
	  RoleRepository roleRepository;
	  
	  @Autowired
	  UserHistoryRepository userHistoryRepository;
	 
	  @Autowired
	  PasswordEncoder encoder;
	 
	  @Autowired
	  JwtProvider jwtProvider;
	  
	  
	  @PostMapping("/signin")
	  public ResponseEntity<?> authenticateUser(@Valid @RequestBody JSONObject loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.get("email").toString(), loginRequest.get("password").toString()));

			System.out.println(authentication.getAuthorities() + " authoritie " + authentication.getName());

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtProvider.generateJwtToken(authentication);
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
			User user1 = user.get();
			user1.setToken(jwt);
			user1.setExpire(false);

			userRepository.save(user1);

			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));

		}catch (Exception ex)
		{
			ex.printStackTrace();
			return  ResponseEntity.ok("error");
		}
	  }

	@PostMapping("/signup")
	public ResponseEntity<String> creationdecompte(@Valid @RequestBody JSONObject userRequest) {
		try {
			System.out.println(userRequest);

			User user = new User();
			user.setUsername(userRequest.get("tel").toString());
			user.setEmail(userRequest.get("email") != null ? userRequest.get("email").toString() :  "");
			user.setPassword (encoder.encode(userRequest.get("password").toString()));
			user.setCreated(LocalDateTime.now());
			user.setExpire(true);
			userRepository.save(user);

			//authenticateUser(userRequest);


			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception ex){
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	  
	  @RequestMapping(value="/profil", method= RequestMethod.GET)
	  public ResponseEntity<User> profil(Principal principal)
	  {
		  try {
			  //System.out.println(principal);
			 Optional<User> user= userRepository.findByUsername(principal.getName());
			  return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		  }catch(UsernameNotFoundException exfound){
			  exfound.getMessage();
			  return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		  }catch(Exception ex)
		  {
			  ex.printStackTrace();
			  return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		  }
	  }




	@RequestMapping(value="/profil/One", method= RequestMethod.POST)
	public ResponseEntity<User> profilOne(@RequestParam("username") String username, @RequestBody JSONObject json, Principal principal)
	{
		try {
			//System.out.println(principal.getName());
			Optional<User> user= userRepository.findByUsername(principal.getName());
			System.out.println(user.get().getUsername());

			User user1 = user.get();
			user1.setEmail(json.get("nouveaumail").toString());
			userRepository.save(user1);

			System.out.println(json);

			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}
	}


	@RequestMapping(value="/profil/num", method= RequestMethod.POST)
	public ResponseEntity<User> profilNum(@RequestParam("username") String username, @RequestBody JSONObject json, Principal principal)
	{
		try {
			//System.out.println(principal.getName());
			Optional<User> user= userRepository.findByUsername(principal.getName());
			System.out.println(user.get().getUsername());

			User user1 = user.get();
			user1.setUsername(json.get("nouveaunum").toString());
			userRepository.save(user1);

			System.out.println(json);

			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}
	}
	  
	  
		@RequestMapping(value="/update/password", method=RequestMethod.POST)
		public ResponseEntity<User> updatepassword(@RequestBody JSONObject json,  Principal principal)
		{
			try {
				System.out.println(json.get("motdepasse").toString());
				if(json.get("nouveaupassword").toString() == json.get("confirmationpassword").toString())
				{
					return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
				}

				Optional<User> user= userRepository.findByUsername(principal.getName());
				
			    Authentication authentication = authenticationManager.authenticate(
			            new UsernamePasswordAuthenticationToken(user.get().getUsername(), json.get("motdepasse").toString()));
				
				if(authentication == null)
				{
					return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
					
				}
				User user1 = userRepository.findByUsername(principal.getName()).get();
				user1.setPassword(encoder.encode(json.get("nouveaupassword").toString()));
				userRepository.save(user1);
				
				return new ResponseEntity<User>(user1, HttpStatus.OK);
			}catch(Exception ex )
			{
				ex.printStackTrace();
				return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
			}
		}
	  
		/**
		 * 
		 * @return
		 */
		  @GetMapping("/first/save")
		  public ResponseEntity<String> save_users()
		  {
				try {
						RoleUser role_msc = new RoleUser();
						role_msc.setName(RoleName.ROLE_ADMIN);
						roleRepository.save(role_msc);
						
						User user = new User(); 
						user.setName("ADMINISTRATEUR ");
						user.setUsername("Admin");
						user.setEmail("admin.postal@laposte.dj");
						 encoder = new BCryptPasswordEncoder(12);
						
						user.setPassword(encoder.encode("admin"));
						Set<RoleUser> rolesevergreen = new HashSet<RoleUser>();
						rolesevergreen.add(role_msc);
						user.setRoles(rolesevergreen);
						
						userRepository.save(user);	
					
					return new ResponseEntity<String>(HttpStatus.OK);
					
				}catch(Exception ex)
				{
					ex.printStackTrace();
					return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
				}
		  }
	  
	 
}
