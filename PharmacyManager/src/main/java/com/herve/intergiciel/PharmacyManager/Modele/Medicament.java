package com.herve.intergiciel.PharmacyManager.Modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medicaments")
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String nom;

    private String description;

    @Column(name = "prix_unitaire", nullable = false)
    private double prixUnitaire;

    @Column(name = "quantite_stock", nullable = false)
    private int quantiteStock;

    @Column(name = "date_expiration")
    @Temporal(TemporalType.DATE)
    private Date dateExpiration;

    @Column(name = "fabriquant")
    private String fabriquant;

    @Column(name = "categorie")
    private String categorie;
}
