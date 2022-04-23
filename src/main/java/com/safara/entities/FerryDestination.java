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
@Table(name="ferry_destination")
public class FerryDestination {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sfidkey;
	private String name;
	private String arrive;
	
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
