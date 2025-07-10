package pro.master.com.Diagnotic.DTO;

import lombok.Data;

@Data
public class PredictionResponse {
    private String maladie;
    private double probabilite;
}

