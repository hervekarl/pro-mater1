package herve.com.pro.rdv.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import herve.com.pro.rdv.DTOS.Patient;

@FeignClient(name = "patient-service", url = "${patient.service.url}")
public interface PatientClient {
    @GetMapping("/patient/{id}")
    Patient searchPatientById(@PathVariable Long id);

    @PostMapping("/patient/exists/{id}")
    boolean patientExists(@PathVariable Long id);

}
