package com.safara.security.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="user_history")
public class UserHistory {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="userhistory", referencedColumnName="id")
	private User user ;
	
	@ManyToOne
	@JoinColumn(name="user_created", referencedColumnName="id")
	private User usercreated;
	
	private LocalDateTime created;
	private LocalDateTime updated;
	
	private String commentaire;

}
