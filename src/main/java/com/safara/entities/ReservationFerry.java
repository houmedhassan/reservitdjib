package com.safara.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name="ferry_booking")
public class ReservationFerry {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int safkey;
	
    private String  destination;
    private int typevoyage;
    private String typevoyagename;
    private LocalDate datedepart;
    private LocalDate dateretour;

    private int nbadult;
    private int nbchild;
    
    private String status;

    private int vehicule;
    private String vehiculetype;
    
    private String etape;
    
    private double montant;
        
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER , mappedBy="reservation")
	@JsonManagedReference
	private Set<PassagerInformation> passagers = new HashSet<PassagerInformation>();
    
	@ManyToOne
	@JoinColumn(referencedColumnName = "id", name = "created")
	private User created;
	
	private LocalDateTime updatedat;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id", name = "updated")
	private User updated;
	
	private String idcrypt;
	
	private String owner;
	private String ownertel;
	
	private String refrence;
	private String invoicenumber;
	private String draftnbr;
	
	
}
