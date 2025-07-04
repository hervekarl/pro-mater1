package com.herve.intergiciel.PatientManager.DTO;

import java.sql.Date;

import com.herve.intergiciel.PatientManager.Enum.Genre;
import com.herve.intergiciel.PatientManager.Enum.GroupeSanguin;
import com.herve.intergiciel.PatientManager.Modeles.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoPatient {

    private Long idPat;
    private String name;
    private String prenom;
    private String tel;
    private String addr;
    private Genre sexe;
    private Date dateN;
    private GroupeSanguin groupeSanguin;

    // 🔁 Ajoute cette méthode ici
    public static DtoPatient fromEntity(Patient patient) {
        return new DtoPatient(
            patient.getIdPat(),
            patient.getName(),
            patient.getPrenom(),
            patient.getTel(),
            patient.getAddr(),
            patient.getSexe(),
            patient.getDateN(),
            patient.getGroupeSanguin()
        );
    }
}
