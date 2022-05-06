package com.safara.entities.dto;

import java.time.LocalDate;

import com.safara.entities.FerryDestination;
import com.safara.entities.FerryTypeVehicule;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReservationFerryMobileDTO {
	
	private FerryDestination destination;
    private LocalDate datedepart;
    private int nbadulte;
    private int nbetudiant;
    private int nbenfant;
    private FerryTypeVehicule vehicule;

}
