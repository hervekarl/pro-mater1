package com.herve.intergiciel.RHManager.RHRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.herve.intergiciel.RHManager.Modeles.Employe;

public interface EmployesRepository extends JpaRepository<Employe, Long> {
   

}
