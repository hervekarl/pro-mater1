package com.herve.intergiciel.PatientManager.Repositories;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.herve.intergiciel.PatientManager.DTO.Medicaments;

@FeignClient(name = "pharmacy-service", url = "${pharmacy.service.url}")
public interface MedicamentClient {

    @GetMapping("/medicaments/{id}")
    Medicaments getMedicamentById(@PathVariable Long id);

    @PostMapping("/medicaments/verify")
    boolean verifyMedicamentAvailability(@RequestBody List<Long> medicamentIds);

}
