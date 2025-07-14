package herve.pro.intergiciel.uploader.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import feign.FeignException;
import herve.pro.intergiciel.uploader.DTO.DTORequest;
import herve.pro.intergiciel.uploader.Exceptions.FIleExceptions;
import herve.pro.intergiciel.uploader.FeignClient.PatientMS;
import herve.pro.intergiciel.uploader.Modele.FilesPatient;
import herve.pro.intergiciel.uploader.Respository.RepositoryUploader;
import lombok.Data;

@Service
@Data
public class UploaderService {
    private final RepositoryUploader repositoryUploader;
    private final PatientMS patientMS;

    public void saveFile(MultipartFile file, DTORequest patient) {
        try {
            // Vérifiez que le patient existe
            Boolean exists = patientMS.patientExists(patient.getPatientid());
            if (exists == null || !exists) {
                throw new FIleExceptions("Patient non trouvé avec l'ID: " + patient.getPatientid());
            }

            FilesPatient fichierToSave = new FilesPatient();
            fichierToSave.setFilename(file.getOriginalFilename());
            fichierToSave.setPatientid(patient.getPatientid());
            repositoryUploader.save(fichierToSave);
            
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Patient non trouvé", e);
        } catch (FeignException e) {
            throw new RuntimeException("Erreur de communication avec le service patient", e);
        }
    }
}