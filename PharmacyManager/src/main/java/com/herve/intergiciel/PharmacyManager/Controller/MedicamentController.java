package com.herve.intergiciel.PharmacyManager.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.herve.intergiciel.PharmacyManager.Dto.MedicamentDTO;
import com.herve.intergiciel.PharmacyManager.Exception.MedicamentNotFoundException;
import com.herve.intergiciel.PharmacyManager.Service.MedicamentService;

import java.util.List;

@RestController
@RequestMapping("/medicaments")
@RequiredArgsConstructor
@Slf4j
// @CrossOrigin(origins = "*") 
public class MedicamentController {
    private final MedicamentService medicamentService;

    @PostMapping
    public ResponseEntity<MedicamentDTO> createMedicament(@RequestBody @Valid MedicamentDTO medicamentDto) {
        log.info("Création d'un nouveau médicament");
        MedicamentDTO createdMedicament = medicamentService.createMedicament(medicamentDto);
        return new ResponseEntity<>(createdMedicament, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MedicamentDTO>> getAllMedicaments() {
        log.info("Récupération de tous les médicaments");
        List<MedicamentDTO> medicaments = medicamentService.getAllMedicaments();
        return ResponseEntity.ok(medicaments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentDTO> getMedicamentById(@PathVariable Long id) {
        log.info("Récupération du médicament avec ID: {}", id);
        MedicamentDTO medicament = medicamentService.getMedicamentById(id);
        return ResponseEntity.ok(medicament);
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> updateMedicament(
            @PathVariable String code,
            @RequestBody @Valid MedicamentDTO medicamentDTO) {
        
        log.info("Tentative de mise à jour du médicament avec code: {}", code);
        try {
            MedicamentDTO updatedMedicament = medicamentService.updateMedicament(code, medicamentDTO);
            log.info("Médicament {} mis à jour avec succès", code);
            return ResponseEntity.ok(updatedMedicament);
        } catch (MedicamentNotFoundException e) {
            log.error("Médicament non trouvé: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour du médicament", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicament(@PathVariable Long id) {
        log.info("Suppression du médicament avec ID: {}", id);
        medicamentService.deleteMedicament(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyMedicamentAvailability(@RequestBody List<Long> medicamentIds) {
        log.info("Vérification de la disponibilité pour {} médicaments", medicamentIds.size());
        boolean allAvailable = medicamentService.verifyMedicamentAvailability(medicamentIds);
        return ResponseEntity.ok(allAvailable);
    }
}