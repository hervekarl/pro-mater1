package com.herve.intergiciel.PatientManager.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.herve.intergiciel.PatientManager.DTO.DtoPatient;
import com.herve.intergiciel.PatientManager.Exceptions.PatientErrorExceptions;
import com.herve.intergiciel.PatientManager.Modeles.Patient;
import com.herve.intergiciel.PatientManager.Repositories.PatientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {
    private PatientRepository infoPatientRepository;

    public Patient create(Patient infoPatient) {
        return this.infoPatientRepository.save(infoPatient);
    }



    public List<DtoPatient> search() {
    return infoPatientRepository.findAll()
            .stream()
            .map(DtoPatient::fromEntity)
            .collect(Collectors.toList());
}

    // @Transactional(readOnly = true)

    public Patient serchPatientById(Long id) {

        return infoPatientRepository.findById(id)
                .orElseThrow(() -> new PatientErrorExceptions(" Not patient with ID " + id));

    }

    public boolean patientexists(Long id) {
        
        return infoPatientRepository.existsById(id);
    }

    public Patient updatePatient(Long id, Patient infoPatient) {
        Patient patientToUpdate = infoPatientRepository.findById(id)
                .orElseThrow(() -> new PatientErrorExceptions("Historique non trouvé avec l'ID : " + id));

        patientToUpdate.setName(infoPatient.getName());
        patientToUpdate.setPrenom(infoPatient.getPrenom());
        patientToUpdate.setTel(infoPatient.getTel());
        patientToUpdate.setDateN(infoPatient.getDateN());
        patientToUpdate.setEmail(infoPatient.getEmail());
        patientToUpdate.setGroupeSanguin(infoPatient.getGroupeSanguin());
        patientToUpdate.setSexe(infoPatient.getSexe());
        patientToUpdate.setAddr(infoPatient.getAddr());

        return infoPatientRepository.save(patientToUpdate);
    }

    public void delete(Long id) {
        if (!infoPatientRepository.existsById(id)) {
            throw new PatientErrorExceptions("Patient not found");
        } else {
            infoPatientRepository.deleteById(id);
        }
    }

}