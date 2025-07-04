package com.herve.intergiciel.PharmacyManager.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.herve.intergiciel.PharmacyManager.Dto.MedicamentDTO;
import com.herve.intergiciel.PharmacyManager.Service.MedicamentService;

import java.util.List;

@RestController
@RequestMapping("/medicaments")
@RequiredArgsConstructor
// @CrossOrigin(origins = "*") // Permettre les requêtes CORS depuis n'importe quelle origine
public class MedicamentController {
    private final MedicamentService medicamentService;

    @PostMapping
    public ResponseEntity<MedicamentDTO> createMedicament(@RequestBody @Valid MedicamentDTO medicamentDto) {
        MedicamentDTO createdMedicament = medicamentService.createMedicament(medicamentDto);
        return new ResponseEntity<>(createdMedicament, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MedicamentDTO>> getAllMedicaments() {
        List<MedicamentDTO> medicaments = medicamentService.getAllMedicaments();
        return ResponseEntity.ok(medicaments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentDTO> getMedicamentById(@PathVariable Long id) {
        MedicamentDTO medicament = medicamentService.getMedicamentById(id);
        return ResponseEntity.ok(medicament);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentDTO> updateMedicament(
            @PathVariable Long id,
            @RequestBody @Valid MedicamentDTO medicamentDTO) {
        MedicamentDTO updatedMedicament = medicamentService.updateMedicament(id, medicamentDTO);
        return ResponseEntity.ok(updatedMedicament);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicament(@PathVariable Long id) {
        medicamentService.deleteMedicament(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyMedicamentAvailability(@RequestBody List<Long> medicamentIds) {
        boolean allAvailable = medicamentService.verifyMedicamentAvailability(medicamentIds);
        return ResponseEntity.ok(allAvailable);
    }

    // @GetMapping("/patient/{patientId}")
    // public ResponseEntity<List<MedicamentDTO>>
    // getMedicamentsByPatientId(@PathVariable Long patientId) {
    // List<MedicamentDTO> medicaments =
    // medicamentService.findByPatientId(patientId);
    // return ResponseEntity.ok(medicaments);
    // }
}
