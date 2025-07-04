package com.herve.intergiciel.PatientManager.Controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.herve.intergiciel.PatientManager.DTO.DtoPatient;
import com.herve.intergiciel.PatientManager.Exceptions.PatientErrorExceptions;
import com.herve.intergiciel.PatientManager.Modeles.Patient;
import com.herve.intergiciel.PatientManager.Services.PatientService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/patient")
@AllArgsConstructor
// @CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
//         RequestMethod.DELETE, RequestMethod.OPTIONS })
public class PatientController {

    private final PatientService infoPatientService;

    @PostMapping(path = "/create", consumes = "application/json")
    public ResponseEntity<Patient> create(@RequestBody Patient infoPatient) {
        return ResponseEntity.ok(infoPatientService.create(infoPatient));
    }

    // @PostMapping(path = "/upload", consumes = "multipart/form-data")
    // public String uploadFile(MultipartFile file) {
    // if (file.isEmpty()) {
    // throw new PatientErrorExceptions("Le fichier est vide");
    // }
    // try {
    // String uploadDir = "uploads/";
    // Path uploadPath = Paths.get(uploadDir);
    // if (!Files.exists(uploadPath)) {
    // Files.createDirectories(uploadPath);
    // }
    // String fileName = file.getOriginalFilename();
    // Path filePath = uploadPath.resolve(fileName);
    // Files.copy(file.getInputStream(), filePath);
    // return filePath.toString(); // retourne le chemin complet
    // } catch (IOException e) {
    // throw new PatientErrorExceptions("Erreur lors de l'upload du fichier : " +
    // e.getMessage());
    // }
    // }
    // @GetMapping("/upload")
    // public ResponseEntity<?> getUploadFiles() {
    // String uploadDir = "uploads/";
    // Path uploadPath = Paths.get(uploadDir);

    // if (!Files.exists(uploadPath) || !Files.isDirectory(uploadPath)) {
    // return ResponseEntity.notFound().build();
    // }

    // try {
    // List<String> fileNames = Files.list(uploadPath)
    // .filter(Files::isRegularFile)
    // .map(path -> path.getFileName().toString())
    // .collect(Collectors.toList());

    // return ResponseEntity.ok(fileNames);
    // } catch (IOException e) {
    // return ResponseEntity.internalServerError().body("Erreur lors de la lecture
    // du dossier: " + e.getMessage());
    // }
    // }

    // @GetMapping("/upload/{fileName}")
    // public ResponseEntity<?> getFile(@PathVariable String fileName) {
    // String uploadDir = "uploads/";
    // Path filePath = Paths.get(uploadDir).resolve(fileName);
    // if (!Files.exists(filePath)) {
    // return ResponseEntity.notFound().build();
    // }
    // try {
    // byte[] fileBytes = Files.readAllBytes(filePath);
    // return ResponseEntity.ok(fileBytes);
    // } catch (IOException e) {
    // return ResponseEntity.internalServerError().body("Erreur lors de la lecture
    // du fichier : " + e.getMessage());
    // }
    // }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> search() {
        List<DtoPatient> patients = infoPatientService.search();
        if (patients.isEmpty()) {
            return ResponseEntity.ok("Aucun patient trouvé");
        }
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> searchPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(infoPatientService.serchPatientById(id));
    }

    @PutMapping(path = "/update/{id}", consumes = "application/json")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Patient infoPatient) {
        if (!id.equals(infoPatient.getIdPat())) {
            return ResponseEntity.badRequest().body("ID dans l'URL ne correspond pas au corps de la requête");
        }
        try {
            return ResponseEntity.ok(infoPatientService.updatePatient(id, infoPatient));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        infoPatientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/exists/{id}")
    public ResponseEntity<Boolean> patientExists(@PathVariable Long id) {
        boolean exists = infoPatientService.patientexists(id);
        if (!exists) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(exists);
    }
}
