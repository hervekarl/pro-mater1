package pro.master.com.Diagnotic.controlleur;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pro.master.com.Diagnotic.DTO.PredictionRequest;
import pro.master.com.Diagnotic.DTO.PredictionResponse;
import pro.master.com.Diagnotic.services.DiagnoticService;

@RestController
@RequestMapping("/api/diagnostic")
public class DiagnosticController {

    private final DiagnoticService diagnoticService;

    public DiagnosticController(DiagnoticService diagnosticService) {
        this.diagnoticService = diagnosticService;
    }

    @PostMapping("/predict")
    public ResponseEntity<PredictionResponse> predict(@RequestBody PredictionRequest request) {
        return ResponseEntity.ok(diagnoticService.diagnotiquer(request));
    }
}
