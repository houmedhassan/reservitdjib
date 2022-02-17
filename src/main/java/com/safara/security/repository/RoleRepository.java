package com.safara.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safara.security.entities.RoleName;
import com.safara.security.entities.RoleUser;


@Repository
public interface RoleRepository extends JpaRepository<RoleUser, Long>{

	Optional<RoleUser> findByName(RoleName roleName);	
	
	
}
