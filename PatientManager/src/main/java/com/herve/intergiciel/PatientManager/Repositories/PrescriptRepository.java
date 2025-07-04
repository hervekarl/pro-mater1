package com.herve.intergiciel.PatientManager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.herve.intergiciel.PatientManager.Modeles.Prescriptions;

public interface PrescriptRepository extends JpaRepository<Prescriptions, Long> {
    // Additional query methods can be defined here if needed

}
