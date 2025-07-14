package herve.pro.intergiciel.uploader.Respository;

import org.springframework.data.jpa.repository.JpaRepository;

import herve.pro.intergiciel.uploader.Modele.FilesPatient;

public interface RepositoryUploader extends JpaRepository<FilesPatient, Long> {
    FilesPatient findByFilename(String name);
}
