package com.herve.intergiciel.PharmacyManager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.herve.intergiciel.PharmacyManager.Modele.Medicament;

import java.util.Optional;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
    Optional<Medicament> findByCode(String code);
    boolean existsByCode(String code);  // Notez le "_" pour accéder à l'ID du Patient
}
