package com.safara.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name="Passager_info")
public class PassagerInformation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String idcrypt;
	
	private String nom;
	private String tel;
	private String addresse;
	
	private String typepersonne; 
	
	private LocalDate datedepart;
	
	@ManyToOne
	@JoinColumn(name="reffkey", referencedColumnName="safkey")
	@JsonIgnore
	private ReservationFerry reservation;

	private LocalDateTime createdat;
	
	private String reference;
	
}
