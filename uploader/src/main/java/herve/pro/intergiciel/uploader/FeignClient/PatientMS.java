package herve.pro.intergiciel.uploader.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import herve.pro.intergiciel.uploader.DTO.Patient;

@FeignClient(name = "patient-service", url = "${patient.service.url}")
public interface PatientMS {

    @GetMapping("/patient/{id}")
    Patient searchPatientById(@PathVariable Long id);
    
    @PostMapping("/patient/exists/{id}")
    boolean patientExists(@PathVariable Long id);
       
}
