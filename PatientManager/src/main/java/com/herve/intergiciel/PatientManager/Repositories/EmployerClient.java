package com.herve.intergiciel.PatientManager.Repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.herve.intergiciel.PatientManager.DTO.DoctorDTO;

@FeignClient(name = "rh-service", url = "${employer.service.url}")
public interface EmployerClient {

    @GetMapping("/rh/employe/{id}")
    DoctorDTO getEmployeById(@PathVariable Long id);

    
    @GetMapping("/rh/employe/exists/{id}")
    boolean employeExists(@PathVariable Long id);

}


