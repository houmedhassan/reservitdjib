package com.safara.entities.administration;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.safara.security.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="entites")
public class Entites {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sfidkey;
	
	private String categorie;
	private String prix;
	private String nomproprietaire;
	private String nom;

	@ManyToOne
	@JoinColumn(referencedColumnName = "sfidkey", name = "region")
	private Region region;
	
	private LocalDateTime createdat;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id", name = "created")
	private User created;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedat;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id", name = "updated")
	private User updated;
	
	private String idcrypt;
	 

}
