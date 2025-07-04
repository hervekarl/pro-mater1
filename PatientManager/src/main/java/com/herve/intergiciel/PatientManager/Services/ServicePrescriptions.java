package com.herve.intergiciel.PatientManager.Services;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.herve.intergiciel.PatientManager.DTO.DtoPrescription;
import com.herve.intergiciel.PatientManager.DTO.Medicaments;
import com.herve.intergiciel.PatientManager.DTO.PrescriptionDetails;
import com.herve.intergiciel.PatientManager.Exceptions.PatientErrorExceptions;
// import com.herve.intergiciel.PatientManager.Modeles.Doctor;
import com.herve.intergiciel.PatientManager.Modeles.Patient;
import com.herve.intergiciel.PatientManager.Modeles.Prescriptions;
// import com.herve.intergiciel.PatientManager.Repositories.DoctorRepository;
import com.herve.intergiciel.PatientManager.Repositories.EmployerClient;
import com.herve.intergiciel.PatientManager.Repositories.MedicamentClient;
import com.herve.intergiciel.PatientManager.Repositories.PatientRepository;
import com.herve.intergiciel.PatientManager.Repositories.PrescriptRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicePrescriptions {
    private final PrescriptRepository prescriptRepository;
    private final MedicamentClient medicamentClient;
    private final PatientRepository patientRepository;
    private final EmployerClient employerExists;
    // private final DoctorRepository doctorRepository; // Nouveau repository
    
    public Prescriptions savePrescription(DtoPrescription dtoPrescription) {
        // Validation
        if (dtoPrescription.getPatientId() == null 
            || dtoPrescription.getDoctorId() == null 
            || dtoPrescription.getMedicaments() == null 
            || dtoPrescription.getMedicaments().isEmpty()) {
            throw new IllegalArgumentException("Patient ID, doctor ID and medicaments list cannot be null or empty");
        }

        // Récupération des entités
        Patient patient = patientRepository.findById(dtoPrescription.getPatientId())
                .orElseThrow(() -> new PatientErrorExceptions("Patient not found"));
        
        // Doctor doctor = doctorRepository.findById(dtoPrescription.getDoctorId())
        //         .orElseThrow(() -> new PatientErrorExceptions("Doctor not found"));

        // Vérification médicaments
        if (!medicamentClient.verifyMedicamentAvailability(dtoPrescription.getMedicaments())) {
            throw new PatientErrorExceptions("One or more medicaments are not available");
        }

        if (!employerExists.employeExists(dtoPrescription.getDoctorId())) {
            throw new PatientErrorExceptions("Doctor does not exist");
            
        }

        // Création prescription
        Prescriptions prescription = new Prescriptions();
        
        prescription.setPatientId(patient);
        prescription.setDoctor(dtoPrescription.getDoctorId()); // Utilisation de l'entité Doctor
        prescription.setMedicaments(dtoPrescription.getMedicaments());
        prescription.setInstructions(dtoPrescription.getInstructions());
        prescription.setDatePrescription(LocalDateTime.now());

        return prescriptRepository.save(prescription);
    }

    // ... (les autres méthodes restent similaires mais doivent être adaptées)

    public PrescriptionDetails getPrescriptionById(Long id) {
        Prescriptions prescription = prescriptRepository.findById(id)
                .orElseThrow(() -> new PatientErrorExceptions("Prescription not found"));

        List<Medicaments> medicamentDetails = prescription.getMedicaments().stream()
                .map(medicamentClient::getMedicamentById)
                .collect(Collectors.toList());

        PrescriptionDetails details = new PrescriptionDetails();
        details.setPatientId(prescription.getPatientId().getIdPat());
        details.setDoctorId(prescription.getDoctor());
        details.setMedicaments(prescription.getMedicaments());
        details.setMedicamentDetails(medicamentDetails);
        details.setInstructions(prescription.getInstructions());
        details.setDatePrescription(prescription.getDatePrescription());
        details.setDateModification(prescription.getDateModification());

        return details;
    }

    public List<Prescriptions> getAllPrescriptions() {
    return prescriptRepository.findAll();
}


}