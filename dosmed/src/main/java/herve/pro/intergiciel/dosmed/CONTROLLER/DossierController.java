package herve.pro.intergiciel.dosmed.CONTROLLER;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import herve.pro.intergiciel.dosmed.DTO.HistoricalRequest;
import herve.pro.intergiciel.dosmed.Modeles.Historical;
import herve.pro.intergiciel.dosmed.Services.HistoricalService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dossier")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DossierController {

    private final HistoricalService historicalService;
    // private final ObjectMapper objectMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createDossier(@RequestBody HistoricalRequest request) {
        try {
            Historical historical = historicalService.createHistorical(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(historical);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflit de données : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur interne : " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Historical>> getAllDossiers() {
        return ResponseEntity.ok(historicalService.getAllHistoricals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Historical> getHistoricalById(@PathVariable Long id) {
        return ResponseEntity.ok(historicalService.getHistoricalById(id));
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> getHistoricalById(@PathVariable Long id) {
    // try {
    // Historical historical = historicalService.getHistoricalById(id);
    // return ResponseEntity.ok(historical);
    // } catch (IllegalArgumentException e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dossier
    // introuvable");
    // } catch (Exception e) {
    // return ResponseEntity.internalServerError().body("Erreur interne : " +
    // e.getMessage());
    // }
    // }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHistorical(
            @PathVariable Long id,
            @RequestBody Historical historical) {
        if (!id.equals(historical.getId())) {
            return ResponseEntity.badRequest().body("ID in URL ne correspond pas au corps de la requête");
        }
        try {
            return ResponseEntity.ok(historicalService.updateHistorical(id, historical));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHistorical(@PathVariable Long id) {
        historicalService.deleteHistorical(id);
        return ResponseEntity.noContent().build();
    }
}
