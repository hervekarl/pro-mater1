package com.herve.intergiciel.PatientManager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.herve.intergiciel.PatientManager.Modeles.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

    

}
