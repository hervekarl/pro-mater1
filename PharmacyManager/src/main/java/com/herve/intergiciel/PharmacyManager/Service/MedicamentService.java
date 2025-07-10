package com.herve.intergiciel.PharmacyManager.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.herve.intergiciel.PharmacyManager.Dto.MedicamentDTO;
import com.herve.intergiciel.PharmacyManager.Exception.MedicamentNotFoundException;
import com.herve.intergiciel.PharmacyManager.Modele.Medicament;
import com.herve.intergiciel.PharmacyManager.Repository.MedicamentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MedicamentService {
    private final MedicamentRepository medicamentRepository;
    private final ModelMapper modelMapper;

    public MedicamentDTO createMedicament(MedicamentDTO medicamentDTO) {
        log.debug("Création d'un nouveau médicament: {}", medicamentDTO);
        Medicament medicament = modelMapper.map(medicamentDTO, Medicament.class);
        Medicament savedMedicament = medicamentRepository.save(medicament);
        return modelMapper.map(savedMedicament, MedicamentDTO.class);
    }

    public List<MedicamentDTO> getAllMedicaments() {
        log.debug("Récupération de tous les médicaments");
        return medicamentRepository.findAll()
                .stream()
                .map(medicament -> modelMapper.map(medicament, MedicamentDTO.class))
                .collect(Collectors.toList());
    }

    public MedicamentDTO getMedicamentById(Long id) {
        log.debug("Recherche du médicament avec ID: {}", id);
        Medicament medicament = medicamentRepository.findById(id)
                .orElseThrow(() -> new MedicamentNotFoundException("Medicament not found with id: " + id));
        return modelMapper.map(medicament, MedicamentDTO.class);
    }

    public MedicamentDTO updateMedicament(String code, MedicamentDTO medicamentDTO) {
        log.debug("Mise à jour du médicament avec code: {}", code);
        Medicament existingMedicament = medicamentRepository.findByCode(code)
                .orElseThrow(() -> new MedicamentNotFoundException("Medicament not found with code: " + code));

        // Mise à jour sélective pour éviter d'écraser les valeurs non fournies
        if (medicamentDTO.getNom() != null) {
            existingMedicament.setNom(medicamentDTO.getNom());
        }
        if (medicamentDTO.getDescription() != null) {
            existingMedicament.setDescription(medicamentDTO.getDescription());
        }
        if (medicamentDTO.getPrixUnitaire() > 0) {
            existingMedicament.setPrixUnitaire(medicamentDTO.getPrixUnitaire());
        }
        if (medicamentDTO.getQuantiteStock() >= 0) {
            existingMedicament.setQuantiteStock(medicamentDTO.getQuantiteStock());
        }
        if (medicamentDTO.getDateExpiration() != null) {
            existingMedicament.setDateExpiration(medicamentDTO.getDateExpiration());
        }
        if (medicamentDTO.getFabriquant() != null) {
            existingMedicament.setFabriquant(medicamentDTO.getFabriquant());
        }
        if (medicamentDTO.getCategorie() != null) {
            existingMedicament.setCategorie(medicamentDTO.getCategorie());
        }

        Medicament updatedMedicament = medicamentRepository.save(existingMedicament);
        log.info("Médicament {} mis à jour avec succès", code);
        return modelMapper.map(updatedMedicament, MedicamentDTO.class);
    }

    public boolean verifyMedicamentAvailability(List<Long> medicamentIds) {
        log.debug("Vérification de la disponibilité pour {} médicaments", medicamentIds.size());
        return medicamentIds.stream()
                .allMatch(id -> medicamentRepository.existsById(id));
    }

    public void deleteMedicament(Long id) {
        log.debug("Suppression du médicament avec ID: {}", id);
        if (!medicamentRepository.existsById(id)) {
            throw new MedicamentNotFoundException("Medicament not found with id: " + id);
        }
        medicamentRepository.deleteById(id);
        log.info("Médicament {} supprimé avec succès", id);
    }
}