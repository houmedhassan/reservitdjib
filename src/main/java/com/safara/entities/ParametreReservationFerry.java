package com.safara.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.safara.security.entities.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "parametre_reservation_ferry")
@Getter
@Setter
public class ParametreReservationFerry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String jour;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "sfidkey", name = "destination")
	private FerryDestination destination;
	
	
	private LocalDateTime createdat;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(referencedColumnName = "id", name = "created")
	private User created;
	
	private LocalDateTime updatedat;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(referencedColumnName = "id", name = "updated")
	private User updated;
	
		
	
}
