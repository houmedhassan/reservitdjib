package com.safara.entities.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReservationFerryDTO {
	
    private int destination;
    private String typevoyage;

    private String datedepart;
    private String dateretour;

    private int nbadult;
    private int nbchild;

    private int vehicule;
    
    private int typebooking;

}
