package pro.master.com.Diagnotic.Client;



import org.springframework.cloud.openfeign.FeignClient; // ✅ Import nécessaire
import org.springframework.web.bind.annotation.*;
import pro.master.com.Diagnotic.DTO.PredictionRequest;
import pro.master.com.Diagnotic.DTO.PredictionResponse;

@FeignClient(name = "predictionClient", url = "http://localhost:8000")
public interface PredictionClient {
    @PostMapping("/predict")
    PredictionResponse predict(@RequestBody PredictionRequest request);
}
