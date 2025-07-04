package com.herve.intergiciel.PharmacyManager.Service;

import lombok.RequiredArgsConstructor;
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
public class MedicamentService {
    private final MedicamentRepository medicamentRepository;
    private final ModelMapper modelMapper;

    public MedicamentDTO createMedicament(MedicamentDTO medicamentDTO) {
        Medicament medicament = modelMapper.map(medicamentDTO, Medicament.class);
        Medicament savedMedicament = medicamentRepository.save(medicament);
        return modelMapper.map(savedMedicament, MedicamentDTO.class);
    }

    public List<MedicamentDTO> getAllMedicaments() {
        return medicamentRepository.findAll()
                .stream()
                .map(medicament -> modelMapper.map(medicament, MedicamentDTO.class))
                .collect(Collectors.toList());
    }

    public MedicamentDTO getMedicamentById(Long id) {
        Medicament medicament = medicamentRepository.findById(id)
                .orElseThrow(() -> new MedicamentNotFoundException("Medicament not found with id: " + id));
        return modelMapper.map(medicament, MedicamentDTO.class);
    }

    public MedicamentDTO updateMedicament(Long id, MedicamentDTO medicamentDTO) {
        Medicament existingMedicament = medicamentRepository.findById(id)
                .orElseThrow(() -> new MedicamentNotFoundException("Medicament not found with id: " + id));

        modelMapper.map(medicamentDTO, existingMedicament);
        // Éviter de mapper l'ID si présent dans le DTO
        existingMedicament.setId(id);
        Medicament updatedMedicament = medicamentRepository.save(existingMedicament);
        return modelMapper.map(updatedMedicament, MedicamentDTO.class);
    }

    public boolean verifyMedicamentAvailability(List<Long> medicamentIds) {
        // Implémentez la logique pour vérifier la disponibilité
        // Par exemple:
        return medicamentIds.stream()
                .allMatch(id -> medicamentRepository.existsById(id));
    }

    public void deleteMedicament(Long id) {
        if (!medicamentRepository.existsById(id)) {
            throw new MedicamentNotFoundException("Medicament not found with id: " + id);
        }
        medicamentRepository.deleteById(id);
    }

    // public List<MedicamentDTO> findByPatientId(Long patientId) {
    // List<Medicament> medicaments =
    // medicamentRepository.findByPatientId(patientId);
    // return medicaments.stream()
    // .map(medicament -> modelMapper.map(medicament, MedicamentDTO.class))
    // .collect(Collectors.toList());
    // }

}