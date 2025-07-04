package com.herve.intergiciel.PatientManager.DTO;

import com.herve.intergiciel.PatientManager.Enum.Genre;

import lombok.Data;

@Data
public class DoctorDTO {
    private Long id;
    private String name;
    private String prenom;
    private Genre sexe;

}

    
