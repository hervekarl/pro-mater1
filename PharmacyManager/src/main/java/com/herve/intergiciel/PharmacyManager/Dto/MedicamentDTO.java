package com.herve.intergiciel.PharmacyManager.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class MedicamentDTO {
    private String code;
    private String nom;
    private String description;
    private double prixUnitaire;
    private int quantiteStock;
    private Date dateExpiration;
    private String fabriquant;
    private String categorie;
}

