package herve.pro.intergiciel.uploader.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import herve.pro.intergiciel.uploader.DTO.DTORequest;
import herve.pro.intergiciel.uploader.Exceptions.FIleExceptions;
import herve.pro.intergiciel.uploader.Services.UploaderService;

@RestController
@RequestMapping("/uploader")
@CrossOrigin(origins = "*")
public class UploadFile {

    private final UploaderService uploaderService;

    public UploadFile(UploaderService uploaderService) {
        this.uploaderService = uploaderService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("patientId") Long patientId) { // Utilisez @RequestParam au lieu de @RequestBody

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Le fichier est vide");
        }

        try {
            // Traitement du fichier
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Path filePath = uploadPath.resolve(fileName);

            if (Files.exists(filePath)) {
                return ResponseEntity.badRequest().body("Un fichier avec ce nom existe déjà");
            }
            if (patientId == null || patientId <= 0) {
                return ResponseEntity.badRequest().body("ID du patient invalide");

            }
            // Vérification de l'existence du patient
            if (!uploaderService.getPatientMS().patientExists(patientId)) {
                return ResponseEntity.badRequest().body("Le patient avec l'ID " + patientId + " n'existe pas");
            } else {

                Files.copy(file.getInputStream(), filePath);

                // Création du DTO
                DTORequest patient = new DTORequest();
                patient.setPatientid(patientId);

                // Enregistrement
                uploaderService.saveFile(file, patient);

                return ResponseEntity.ok().body("Fichier uploadé avec succès pour le patient " + patientId);

            }

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur lors de l'upload du fichier: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUploadFiles() {
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath) || !Files.isDirectory(uploadPath)) {
            return ResponseEntity.badRequest().body("Pas de fichier disponible");
        }

        try {
            List<String> fileNames = Files.list(uploadPath)
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());

            if (fileNames.isEmpty()) {
                return ResponseEntity.ok().body("Aucun fichier trouvé");
            }

            return ResponseEntity.ok(fileNames);
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur lors de la lecture du dossier: " + e.getMessage());
        }
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> getFile(@PathVariable String fileName) {
        String uploadDir = "uploads/";
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }
        try {
            byte[] fileBytes = Files.readAllBytes(filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .body(fileBytes);
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
}