package herve.com.pro.rdv.Modele;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Rendezvous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patient;

    @Column(nullable = false)
    private Long personnel;

    private String title;

    private String motif;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String lieu;

}
