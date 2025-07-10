package pro.master.com.Diagnotic.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
public class PredictionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;
    private String sexe;
    private String fumeur;
    private double tension;
    private double temperature;
    private String symptomes;

    private String maladie;
    private double probabilite;

    private LocalDateTime horodatage = LocalDateTime.now();

    public static Object builder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'builder'");
    }
}
