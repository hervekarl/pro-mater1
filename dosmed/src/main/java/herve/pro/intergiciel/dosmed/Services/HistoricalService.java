package herve.pro.intergiciel.dosmed.Services;

import herve.pro.intergiciel.dosmed.DTO.HistoricalRequest;
import herve.pro.intergiciel.dosmed.Exceptions.HistoricalNotFoundException;
import herve.pro.intergiciel.dosmed.Repository.HistoricalRepository;
import herve.pro.intergiciel.dosmed.feignClient.PatientServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import herve.pro.intergiciel.dosmed.Modeles.Historical;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HistoricalService {

    private final HistoricalRepository historicalRepository;
    private final PatientServiceClient patientServiceClient;

    public  Historical createHistorical(HistoricalRequest historical) {
        try {
            Historical historicalEntity = new Historical();
            
            historicalEntity.setAllergy(historical.getAllergy());
            historicalEntity.setAntecedent(historical.getAntecedent());
            historicalEntity.setMalEncours(historical.getMalEncours());
            historicalEntity.setDocuments(historical.getDocuments());
            historicalEntity.setPatient(historical.getPatient());
            
            if (!patientServiceClient.patientExists(historical.getPatient())) {
                throw new HistoricalNotFoundException("Patient non trouvé avec l'ID : " + historical.getPatient());
            } else {

                return historicalRepository.save(historicalEntity);
            }

        } catch (DataIntegrityViolationException e) {
            throw new HistoricalNotFoundException("Violation de contrainte : " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Historical> getAllHistoricals() {
        return historicalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Historical getHistoricalById(Long id) {
        return historicalRepository.findById(id)
                .orElseThrow(() -> new HistoricalNotFoundException("Historique non trouvé avec l'ID : " + id));
    }

    public Historical updateHistorical(Long id, Historical historicalDetails) {
        Historical historical = historicalRepository.findById(id)
                .orElseThrow(() -> new HistoricalNotFoundException("Historique non trouvé avec l'ID : " + id));

        historical.setAllergy(historicalDetails.getAllergy());
        historical.setAntecedent(historicalDetails.getAntecedent());
        historical.setMalEncours(historicalDetails.getMalEncours());
        historical.setDocuments(historicalDetails.getDocuments());
        
        // Ne mettez à jour le patient que si nécessaire (car champ unique)
        if (!historical.getPatient().equals(historicalDetails.getPatient())) {
            historical.setPatient(historicalDetails.getPatient());
        }

        return historicalRepository.save(historical);
    }

    public void deleteHistorical(Long id) {
        if (!historicalRepository.existsById(id)) {
            throw new HistoricalNotFoundException("Historique non trouvé avec l'ID : " + id);
        }
        historicalRepository.deleteById(id);
    }


}