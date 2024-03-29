package com.safara.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safara.security.entities.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
	//User findByUsername(String name);
	List<User> findAll();

	User findByEmail(String email);

	User findByTel(String tel);
	
}
