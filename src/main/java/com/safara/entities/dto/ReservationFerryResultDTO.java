package com.safara.entities.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationFerryResultDTO {
	
    private String  destination;
    private String typevoyagename;
    private LocalDate datedepart;

    private int nbadult;
    private int nbchild;

    private String vehiculetype;
    
    private double somme;
    private double total;

}
