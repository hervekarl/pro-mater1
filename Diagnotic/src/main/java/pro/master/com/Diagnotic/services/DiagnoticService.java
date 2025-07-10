package pro.master.com.Diagnotic.services;

import org.springframework.stereotype.Service;
import pro.master.com.Diagnotic.Client.PredictionClient; // <-- maintenant avec P majuscule
import pro.master.com.Diagnotic.DTO.PredictionRequest;
import pro.master.com.Diagnotic.DTO.PredictionResponse;

@Service
public class DiagnoticService {
    private final PredictionClient predictionClient;

    public DiagnoticService(PredictionClient predictionClient) {
        this.predictionClient = predictionClient;
    }

    public PredictionResponse diagnotiquer(PredictionRequest request) {
        if (request.getSymptomes() == null || request.getSymptomes().size() < 3) {
            throw new IllegalArgumentException("Au moins 3 symptômes sont requis");
        }
        return predictionClient.predict(request);
    }
}
