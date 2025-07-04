package herve.pro.intergiciel.dosmed.Modeles;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

// import herve.pro.intergiciel.dosmed.CONVERTIR.AllergyListConverter;
// import herve.pro.intergiciel.dosmed.CONVERTIR.AntecedentListConverter;
// import herve.pro.intergiciel.dosmed.CONVERTIR.DocumentListConverter;
// import herve.pro.intergiciel.dosmed.CONVERTIR.JsonConverter;
// import herve.pro.intergiciel.dosmed.CONVERTIR.MaladieListConverter;
// import herve.pro.intergiciel.dosmed.CONVERTIR.PatientConverter;
import herve.pro.intergiciel.dosmed.DTO.HistoricalRequest;

@Entity
@Table(name = "dossier_patient")
@Data
public class Historical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patient;
   
    private List<String> allergy;

    private List<String> antecedent;

    private List<String> malEncours;
  
    private List<String> documents;

    

}
