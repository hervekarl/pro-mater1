package pro.master.com.Diagnotic.DTO;

import java.util.List;

import lombok.Data;

@Data
public class PredictionRequest {
    private int age;
    private String sexe;
    private String fumeur;
    private double tension;
    private double temperature;
    private List<String> symptomes;
}

