package com.safara.entities.administration;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.safara.entities.FerryTypeVehicule;
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
@Table(name="ferry_tarif")
public class FerryTarif {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sfidkey;
	private String type;
	private double montant;

}
