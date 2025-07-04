package com.herve.intergiciel.PatientManager.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.herve.intergiciel.PatientManager.DTO.DtoPrescription;
import com.herve.intergiciel.PatientManager.Modeles.Prescriptions;
import com.herve.intergiciel.PatientManager.Services.ServicePrescriptions;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/prescriptions")
@RequiredArgsConstructor
public class PrescriptionsController {

    private final ServicePrescriptions prescriptionService;

    @PostMapping("/create")
    public ResponseEntity<Prescriptions> createPrescription(@RequestBody DtoPrescription prescription) {
        // Here you would typically call a service to handle the business logic
        // For now, we will just return a new Prescriptions object based on the DTO

        if (prescription.getPatientId() == null || prescription.getMedicaments() == null || prescription.getMedicaments().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        // Save the prescription to the database (not implemented here)
        // prescriptionService.save(newPrescription);

        return ResponseEntity.ok(prescriptionService.savePrescription(prescription));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

}
