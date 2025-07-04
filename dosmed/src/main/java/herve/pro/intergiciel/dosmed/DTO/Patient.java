package herve.pro.intergiciel.dosmed.DTO;


import lombok.Data;

@Data
public class Patient {
    private Long id;
    private String name;
    private String prenom;
    private String dateNaissance;
    private String sexe;
    private String adresse;
    private String telephone;
    private String email;
    private String groupeSanguin;
   

}
