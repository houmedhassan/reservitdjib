package com.safara.entities.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RapportInProgressDTO {

    int ref;
    String destination;
    LocalDate datedepart;
    double montant;
    String idcrypt;
}
