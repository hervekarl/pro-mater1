package com.herve.intergiciel.RHManager.Modeles;


import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employes")
@Setter
@Getter
public class Employe {
    public enum Genre{HOMME, FEMME, AUTRE, homme, femme, autre, Homme, Femme, H, F, h, f, masculin, feminin, FEMININ, MASCULIN, M}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ce champ est obligatoire")
    private String nom;
    private String prenom;

    @NotNull
    private Genre sexe;

    @DateTimeFormat(pattern = "yyyy-MM-jj")
    @NotNull
    @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "La date de naissance doit être au format yyyy-MM-jj.")
    private String dateNaissance;

    @Pattern(regexp = "^[+00][0-9]*$", message = "Le numéro de téléphone doit contenir uniquement des chiffres.")
    private String telephone;

    private String adresse;

    @Email
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+$", message = "L'adresse e-mail n'est pas valide.")
    @NotNull
    private String email;


    @DateTimeFormat(pattern = "yyyy-MM-jj")
    private String dateEmbauche;
    
//     @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//     private List<Document> documents = new ArrayList<>();
// }

}
