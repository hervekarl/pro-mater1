package com.herve.intergiciel.PatientManager.DTO;

import lombok.Data;

@Data
public class Medicaments {
    private Long id;
    private String nom;
    private String description;
    private String dosage;
}
