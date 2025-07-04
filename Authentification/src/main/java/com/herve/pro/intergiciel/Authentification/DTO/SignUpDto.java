package com.herve.pro.intergiciel.Authentification.DTO;


import jakarta.validation.constraints.*;
import lombok.Data;
import java.sql.Date;

@Data
public class SignUpDto {
    @NotBlank(message = "Le nom est obligatoire")
    private String lastname;

    @NotBlank(message = "Le prénom est obligatoire")
    private String firstname;

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 20, message = "Le nom d'utilisateur doit contenir entre 3 et 20 caractères")
    private String username;

    private Date birthdate;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;

    @Pattern(regexp = "^[+0][0-9]*$", message = "Le téléphone doit commencer par + ou 0")
    private String phone;
}